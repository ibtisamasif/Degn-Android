package com.example.tradingapp.compose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tradingapp.compose.BottomNavigationBar
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.data.Transaction
import com.example.tradingapp.viewModels.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun ActivityScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onItemSelected: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchTransaction(0)
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) { Title(title = "Activity") { onItemSelected.invoke("Back") } }
        },
        bottomBar = { BottomNavigationBar(0) {} }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            val transactions = viewModel.transactions.collectAsState().value
            if (transactions != null) {
                TransactionList(viewModel = viewModel, transactions){
                    onItemSelected.invoke(it)
                }
            }
        }
    }
}

@Composable
fun TransactionList(viewModel: HomeViewModel, transactions: List<Transaction>,onItemSelected: (String) -> Unit,) {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    val sortedTransactions = transactions.sortedWith(compareByDescending<Transaction> {
        Instant.parse(it.createdAt).atZone(ZoneId.systemDefault()).toLocalDate()
    }.thenByDescending {
        Instant.parse(it.createdAt).atZone(ZoneId.systemDefault()).toLocalTime()
    })

    val groupedTransactions = sortedTransactions.groupBy { it.createdAt.split("T")[0] }

    LazyColumn {
        groupedTransactions.forEach { (date, transactionsForDate) ->
            item {
                Text(
                    text = viewModel.formatDate(date),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            items(transactionsForDate.size) { transaction ->
                TransactionItem(viewModel = viewModel, transactionsForDate[transaction]){
                    onItemSelected.invoke(it)
                }
            }
        }
    }
}

@Composable
fun TransactionItem(viewModel: HomeViewModel, transaction: Transaction, onItemSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { onItemSelected.invoke("id") }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = transaction.token.icon,
            contentDescription = "Profile Picture from URL",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.token.name,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
            )

            val formattedTime = viewModel.formatTime(transaction.createdAt)
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "${if (transaction.type == "buy") "+$" else "-$"}${transaction.amount}",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
            color = if (transaction.type == "buy") Color.Green else Color.Red,
        )
    }
}

