package com.example.tradingapp.viewModels.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.data.BalanceBody
import com.example.tradingapp.data.TokenAccount
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

    private val _userTokenBalance = MutableStateFlow<BalanceBody?>(null)
    val userTokenBalance: StateFlow<BalanceBody?> = _userTokenBalance

    private val _userBalance = MutableStateFlow<BalanceBody?>(null)
    val userBalance: StateFlow<BalanceBody?> = _userBalance

    var isTokensLoaded = false

    val offset = mutableIntStateOf(0)
    val selectedCoinId = mutableStateOf("")
    val tokenCreationDate = mutableStateOf("")
    private val _tokenBalance = MutableStateFlow<String?>(null)
    private val _tokenQuantity = MutableStateFlow<String?>(null)
    val tokenBalance: StateFlow<String?> = _tokenBalance
    val tokenQuantity: StateFlow<String?> = _tokenQuantity
    private val limit = 10

    private var allTokensLoaded = mutableStateOf(false)
    var newItemLoaded = mutableStateOf(false)
    fun fetchUserBalance() {
        viewModelScope.launch {
            val response = pref.getAccessToken()?.let { walletRepo.getUserBalance(it)}
            _userBalance.value = response?.body
        }
    }

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

    suspend fun fetchTransaction(offset: Int, limit: Int = 100) {
        val response =
            pref.getAccessToken()?.let { transactionRepo.getTransactions(it, offset, limit) }
        _transactions.value = response?.body?.tranactions
    }

    suspend fun buyTransaction(amount: String) {
        val request = token.value?.tokenAddress?.let { tokenAddress ->
            TransactionRequest(amount = amount, token = tokenAddress)
        }
        val response = pref.getAccessToken()?.let { accessToken ->
            if (request != null) {
                transactionRepo.buyTransaction(accessToken, request)
//                fetchUserTokenBalance(selectedCoinId.value)
                transaction.value = ""
            }
        }
    }

    suspend fun sellTransaction(amount: String) {
        val request =
            token.value?.tokenAddress?.let { TransactionRequest(amount = amount, token = it) }
        val response = pref.getAccessToken()?.let {
            if (request != null) {
                transactionRepo.sellTransaction(it, request)
                transaction.value = ""
            }
        }
    }

    private fun checkTokenBalance(listTokenAccount: List<TokenAccount>) {
        val isTokenPresent = listTokenAccount.find { it.mintAddress == token.value?.tokenAddress }
        if (isTokenPresent != null) {
            _tokenBalance.value = isTokenPresent.valueInUSD.toString()
            _tokenQuantity.value = isTokenPresent.balance.toString()
        }else{
            _tokenBalance.value = null
            _tokenQuantity.value = null
        }
    }


    suspend fun fetchUserTokenBalance(id: String) {
        val response = pref.getAccessToken()?.let { walletRepo.getUserTokenBalance(it, id) }
        _userTokenBalance.value = response?.body
        response?.body?.tokenAccounts?.let { checkTokenBalance(it) }
        isLoading.value = false
    }

    suspend fun fetchBalance() {
        val response = pref.getAccessToken()?.let { walletRepo.getUserBalance(it) }
        _userBalance.value = response?.body
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

    private fun numberIntoDecimals(amount: String, decimals: Int): String {
        val amountBig = BigDecimal(amount)
        val decimalsBig = BigDecimal.TEN.pow(decimals)
        var amountFormatted = amountBig.multiply(decimalsBig)
        amountFormatted = amountFormatted.stripTrailingZeros()
        if (amountFormatted.scale() > 0) {
            amountFormatted = amountFormatted.setScale(0, RoundingMode.DOWN)
        }
        return amountFormatted.toPlainString()
    }

    suspend fun getQuote(){
//        val response = token.value?.let {it-> dashboardRepo.getQuote(it.tokenAddress,
//            tokenBalance.value?.let { it1 -> numberIntoDecimals(it1,it.decimals) }) }
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