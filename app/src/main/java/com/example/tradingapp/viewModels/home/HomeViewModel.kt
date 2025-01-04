package com.example.tradingapp.viewModels.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.data.BalanceBody
import com.example.tradingapp.data.QuoteResponse
import com.example.tradingapp.data.TokenDetails
import com.example.tradingapp.data.Transaction
import com.example.tradingapp.data.TransactionRequest
import com.example.tradingapp.di.pref.DegnSharedPref
import com.example.tradingapp.repo.DashboardRepo
import com.example.tradingapp.repo.TransactionRepo
import com.example.tradingapp.repo.WalletRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

class HomeViewModel(
    private val dashboardRepo: DashboardRepo,
    private val transactionRepo: TransactionRepo,
    private val walletRepo: WalletRepo,
    private val pref: DegnSharedPref
) : ViewModel() {
    val isLoading = mutableStateOf(true)
    val transaction = mutableStateOf("")
    private val _spotlightTokens = MutableStateFlow<List<TokenDetails>?>(null)
    val spotlightTokens: StateFlow<List<TokenDetails>?> = _spotlightTokens

    private val _tokens = MutableStateFlow<List<TokenDetails>?>(null)
    val tokens: StateFlow<List<TokenDetails>?> = _tokens

    private val _token = MutableStateFlow<TokenDetails?>(null)
    val token: StateFlow<TokenDetails?> = _token

    private val _transactions = MutableStateFlow<List<Transaction>?>(null)
    val transactions: StateFlow<List<Transaction>?> = _transactions

    private val _userBalance = MutableStateFlow<BalanceBody?>(null)
    val userBalance: StateFlow<BalanceBody?> = _userBalance

    val quoteLiveData = MutableLiveData<QuoteResponse>()

    var isTokensLoaded = false

    val offset = mutableIntStateOf(0)
    val selectedCoinId = mutableStateOf("")
    val tokenCreationDate = mutableStateOf("")
    private val limit = 10

    private var allTokensLoaded = mutableStateOf(false)
    var newItemLoaded = mutableStateOf(false)

    fun fetchTokens() {
        if (allTokensLoaded.value) return

        viewModelScope.launch {
            val response = pref.getAccessToken()?.let {
                dashboardRepo.getTokens(it, offset.intValue, limit)
            }
            val newTokens = response?.body?.tokens ?: emptyList()
            _tokens.value = if (_tokens.value == null) newTokens else _tokens.value!! + newTokens
            if (newTokens.isEmpty()) {
                allTokensLoaded.value = true
            }
            isLoading.value = false
            newItemLoaded.value = true
        }
    }

    fun fetchSpotlightTokens() {
        viewModelScope.launch {
            val response = pref.getAccessToken()
                ?.let { dashboardRepo.getSpotlightTokens(it, offset.intValue, limit) }
            _spotlightTokens.value = response?.body?.tokens
        }
    }

    suspend fun fetchTokenByID(id: String) {
        val response = pref.getAccessToken()?.let { dashboardRepo.getTokenById(it, id) }
        _token.value = response?.body?.token
        tokenCreationDate.value =
            formatTimeDifference(response?.body?.token?.tokenCreatedAt.toString())
        isLoading.value = false
    }

    suspend fun fetchTransaction(offset: Int,limit: Int =100){
        val response = pref.getAccessToken()?.let { transactionRepo.getTransactions(it,offset,limit) }
        _transactions.value = response?.body?.tranactions
    }

    fun buyTransaction() {
        transaction.value = "Buy"
        viewModelScope.launch {
            val request = token.value?.tokenAddress?.let { TransactionRequest(amount = "2.5", token = it) }
            val response = pref.getAccessToken()?.let { if (request != null) { transactionRepo.buyTransaction(it, request) }
            }
            isLoading.value = false
        }
    }

    fun sellTransaction() {
        transaction.value = "Sell"
        viewModelScope.launch {
            val request = token.value?.tokenAddress?.let { TransactionRequest(amount = "2.5", token = it) }
            val response = pref.getAccessToken()?.let {
                    if (request != null) {
                        transactionRepo.sellTransaction(it, request)
                    }
                }
            isLoading.value = false
        }
    }

    suspend fun fetchUserBalance(id: String) {
        val response = pref.getAccessToken()?.let { walletRepo.getUserBalance(it, id) }
        _userBalance.value = response?.body
    }

    fun fetchQuote(inputMint: String, amount: Long) {
        viewModelScope.launch {
            val quote = dashboardRepo.getQuote(inputMint, amount)
            quote?.let {
                quoteLiveData.postValue(it)
            }
        }
    }

    private fun formatTimeDifference(isoDateString: String): String {
        val inputDateTime = Instant.parse(isoDateString)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        val now = LocalDateTime.now()

        val isFuture = inputDateTime.isAfter(now)

        var start = if (isFuture) now else inputDateTime
        val end = if (isFuture) inputDateTime else now

        val years = ChronoUnit.YEARS.between(start, end)
        start = start.plusYears(years)

        val months = ChronoUnit.MONTHS.between(start, end)
        start = start.plusMonths(months)

        val days = ChronoUnit.DAYS.between(start, end)
        start = start.plusDays(days)

        val hours = ChronoUnit.HOURS.between(start, end)

        val builder = StringBuilder()

        if (years > 0) builder.append("${years}y ")
        if (months > 0) builder.append("${months}m ")
        if (days > 0) builder.append("${days}d ")
        if (hours > 0) builder.append("${hours}h ")

        val result = builder.toString().trim()
        return if (result.isEmpty()) {
            "Just now"
        } else {
            "$result ${if (isFuture) "to go" else "ago"}"
        }
    }

    fun numberIntoDecimals(amount: String, decimals: Int): String {
        var amountBig = BigDecimal(amount)
        val decimalsBig = BigDecimal.TEN.pow(decimals)
        var amountFormatted = amountBig.multiply(decimalsBig)
        amountFormatted = amountFormatted.stripTrailingZeros()
        if (amountFormatted.scale() > 0) {
            amountFormatted = amountFormatted.setScale(0, RoundingMode.DOWN)
        }
        return amountFormatted.toPlainString()
    }


    fun formatTime(inputTime: String): String {
        val instant = Instant.parse(inputTime)
        val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()
        val outputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault())
        return localTime.format(outputFormatter)
    }

    fun formatDate(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val date = LocalDate.parse(inputDate, inputFormatter)

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return when (date) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault()))
        }
    }

}