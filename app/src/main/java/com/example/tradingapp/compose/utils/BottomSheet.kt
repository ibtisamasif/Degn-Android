package com.example.tradingapp.compose.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tradingapp.compose.account.GainsScreen
import com.example.tradingapp.compose.payment.PaymentScreen
import com.example.tradingapp.compose.search.SearchScreen
import com.example.tradingapp.compose.support.SupportScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(screenName: String, onCloseBottomSheet: (Boolean) -> Unit) {
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {onCloseBottomSheet.invoke(false)},
        containerColor = Color.White
    ) {
        when (screenName) {
            "Gain" -> GainsScreen{onCloseBottomSheet.invoke(!it)}
            "Send" -> PaymentScreen{}
            "Withdraw" -> PaymentScreen{}
            "Deposit" -> PaymentScreen{}
            "Support" -> SupportScreen {onCloseBottomSheet.invoke(!it)}
            "Search" -> SearchScreen{onCloseBottomSheet.invoke(!it)}
        }
    }
}