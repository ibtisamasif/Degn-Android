package com.example.tradingapp.compose.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.Title

@Composable
fun CashMiniScreen(onCloseBottomSheet: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Title(title = "Select balance", isBottomSheet = true) {
            onCloseBottomSheet.invoke(true)
        }
        Spacer(modifier = Modifier.height(16.dp))
        CashRow(R.drawable.dollar, "Cash:", "$0.00"){onCloseBottomSheet.invoke(true)}
        CashRow(R.drawable.solana_cash, "Solana:", "$0.00"){onCloseBottomSheet.invoke(true)}

    }
}

@Composable
fun CashRow(
    icon: Int,
    text: String,
    payment: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick.invoke() }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.5f)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Cash",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.5f).padding(top = 4.dp)
        ) {
            Text(
                text = payment,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}