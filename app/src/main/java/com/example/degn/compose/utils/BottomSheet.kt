package com.example.degn.compose.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.degn.compose.account.GainsScreen
import com.example.degn.compose.export.SecurePhraseScreen
import com.example.degn.compose.payment.CashMiniScreen
import com.example.degn.compose.payment.DepositMiniScreen
import com.example.degn.compose.payment.PaymentScreen
import com.example.degn.compose.search.SearchScreen
import com.example.degn.compose.support.SupportScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(screenName: String,currentBalance: Double = 0.00, onCloseBottomSheet: (Boolean) -> Unit, amount: (String) -> Unit) {
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
                currentBalance=currentBalance,
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = {}
            )
            "Withdraw" -> PaymentScreen(
                title = "Withdraw",
                currentBalance=currentBalance,
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = {}
            )
            "Deposit" -> PaymentScreen(
                title = "Deposit",
                currentBalance=currentBalance,
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = {}
            )
            "Buy" -> PaymentScreen(
                title = "Buy",
                currentBalance=currentBalance,
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = { amount.invoke(it) }
            )
            "Sell" -> PaymentScreen(
                title = "Sell",
                currentBalance=currentBalance,
                onCloseBottomSheet = { onCloseBottomSheet.invoke(!it) },
                amountString = { amount.invoke(it) }
            )
            "Support" -> SupportScreen { onCloseBottomSheet.invoke(!it) }
            "Search" -> SearchScreen { onCloseBottomSheet.invoke(!it) }
            "CashMini" -> CashMiniScreen { onCloseBottomSheet.invoke(!it) }
            "DepositMini" -> DepositMiniScreen()
            "SecretKey" -> SecurePhraseScreen()
            "Slippage" -> SlippageScreen{ onCloseBottomSheet.invoke(!it)}
        }
    }
}
