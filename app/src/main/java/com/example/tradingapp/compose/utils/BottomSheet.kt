package com.example.tradingapp.compose.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tradingapp.compose.account.GainsScreen
import com.example.tradingapp.compose.export.SecurePhraseScreen
import com.example.tradingapp.compose.payment.CashMiniScreen
import com.example.tradingapp.compose.payment.DepositMiniScreen
import com.example.tradingapp.compose.payment.PaymentScreen
import com.example.tradingapp.compose.search.SearchScreen
import com.example.tradingapp.compose.support.SupportScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(screenName: String, onCloseBottomSheet: (Boolean) -> Unit, amount: (String) -> Unit) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { false }
    )

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {},
        containerColor = Color.White
    ) {
        when (screenName) {
            "Gain" -> GainsScreen { onCloseBottomSheet.invoke(!it) }
            "Send" -> PaymentScreen(
                title = "Send",
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = {}
            )
            "Withdraw" -> PaymentScreen(
                title = "Withdraw",
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = {}
            )
            "Deposit" -> PaymentScreen(
                title = "Deposit",
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = {}
            )
            "Buy" -> PaymentScreen(
                title = "Buy",
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = { amount.invoke(it) }
            )
            "Sell" -> PaymentScreen(
                title = "Sell",
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = { amount.invoke(it) }
            )
            "Support" -> SupportScreen { onCloseBottomSheet.invoke(!it) }
            "Search" -> SearchScreen { onCloseBottomSheet.invoke(!it) }
            "CashMini" -> CashMiniScreen { onCloseBottomSheet.invoke(!it) }
            "DepositMini" -> DepositMiniScreen()
            "SecretKey" -> SecurePhraseScreen()
        }
    }
}
