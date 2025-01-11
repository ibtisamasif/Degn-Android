package com.example.degn.compose.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.degn.R
import com.example.degn.compose.utils.BottomSheet
import com.example.degn.compose.utils.ConfirmationButton
import com.example.degn.compose.utils.Title
import com.example.degn.ui.theme.Purple
import com.example.degn.viewModels.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PaymentScreen(
    title: String,
    currentBalance: Double,
    onCloseBottomSheet: (Boolean) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    amountString: (String) -> Unit
) {
    var amount by remember { mutableStateOf("0.00") }
    var screenName by remember { mutableStateOf("") }
    var isUiEnabled by remember { mutableStateOf(true) }

    val tokenQuantity = viewModel.tokenQuantity.collectAsState().value
    val token = viewModel.token.collectAsState().value

    val quantity = tokenQuantity?.toDoubleOrNull() ?: 0.0
    val price = token?.price?.toDoubleOrNull() ?: 0.0
    val totalValue = if(title == "Sell") price * quantity else currentBalance

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Title(
            title = title,
            isBottomSheet = true
        ) {
            if (isUiEnabled){
                when(it){
                    "Back"->onCloseBottomSheet.invoke(true)
                    "Slippage"-> screenName = it
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectCashBox(
                currentBalance = currentBalance,
                isUiEnabled = isUiEnabled,
                onCashBoxClick = { screenName = "CashMini" }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$$amount",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            if (title !in listOf("Send", "Buy", "Sell")) {
                Spacer(modifier = Modifier.height(24.dp))
                SelectCardBox(
                    isUiEnabled = isUiEnabled,
                    onCardBoxClick = { screenName = "DepositMini" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            PercentageSelectionRow(
                totalValue = totalValue,
                currentAmount = amount,
                isUiEnabled = isUiEnabled
            ) { selectedAmount ->
                amount = selectedAmount
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        NumericKeypad(onKeyPress = { key ->
            if (isUiEnabled) {
                amount = updateAmount(amount, key, currentBalance)

            }
        })

        Row(
            modifier = Modifier
                .weight(0.8f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConfirmationButton {
                if (isUiEnabled) {
                    amountString.invoke(amount)
                    isUiEnabled = false
                }
            }
        }
    }

    if (screenName.isNotEmpty()) {
        BottomSheet(
            screenName = screenName,
            onCloseBottomSheet = { screenName = "" },
            amount = {}
        )
    }
}

@Composable
private fun SelectCashBox(
    currentBalance: Double,
    isUiEnabled: Boolean,
    onCashBoxClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(1.dp)
            .clickable(enabled = isUiEnabled) { onCashBoxClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(21.5.dp)
                )
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Cash:",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "$${"%.2f".format(currentBalance)}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "cash",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
@Composable
private fun SlipPageBox(
    currentBalance: Double,
    isUiEnabled: Boolean,
    onCashBoxClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(1.dp)
            .clickable(enabled = isUiEnabled) { onCashBoxClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(21.5.dp)
                )
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Cash:",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "$${"%.2f".format(currentBalance)}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "cash",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun SelectCardBox(
    isUiEnabled: Boolean,
    onCardBoxClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .padding(1.dp)
            .clickable(enabled = isUiEnabled) { onCardBoxClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(21.5.dp)
                )
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = null
            )
            Text(
                text = "Credit/Debit Card",
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "card",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun PercentageSelectionRow(
    totalValue: Double,
    currentAmount: String,
    isUiEnabled: Boolean,
    onAmountSelected: (String) -> Unit
) {
    var selectedPercentage by remember { mutableStateOf("") }
    val percentages = listOf("10%", "25%", "50%", "MAX")

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        percentages.forEach { label ->
            val percentage = label.toPercentage()
            val truncatedValue = (totalValue * percentage).truncateToFourDecimalPlacesNoRounding()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (selectedPercentage == label) Purple else Color.White
                    )
                    .clickable(enabled = isUiEnabled) {
                        selectedPercentage = if(selectedPercentage == label) "" else label
                        onAmountSelected(truncatedValue)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
                    color = if (selectedPercentage == label) Color.White else Color.Black
                )
            }
        }
    }
}

@Composable
fun NumericKeypad(onKeyPress: (String) -> Unit) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { key ->
                    Button(
                        onClick = { onKeyPress(key) },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            containerColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        modifier = Modifier.size(68.dp)
                    ) {
                        if (key.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.clear),
                                contentDescription = "Clear",
                                modifier = Modifier.size(48.dp)
                            )
                        } else {
                            Text(text = key, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}

private fun updateAmount(
    currentAmount: String,
    key: String,
    currentBalance: Double
): String {
    val newAmountStr = when (key) {
        "" -> {
            if (currentAmount.isNotEmpty()) currentAmount.dropLast(1).ifEmpty { "0" } else "0"
        }

        "." -> {
            if (currentAmount.isEmpty()) {
                "0."
            } else if (currentAmount.contains(".")) {
                currentAmount
            } else {
                "$currentAmount."
            }
        }

        else -> {
            if (currentAmount == "0" || currentAmount == "0.0000") {
                if (key == "0") "0" else key
            } else {
                currentAmount + key
            }
        }
    }

    val newAmountDouble = newAmountStr.toDoubleOrNull() ?: 0.0
    return if (newAmountDouble > currentBalance) {
        currentAmount
    } else {
        newAmountStr
    }
}

private fun String.toPercentage(): Double {
    return when (this) {
        "10%" -> 0.10
        "25%" -> 0.25
        "50%" -> 0.50
        "MAX" -> 1.00
        else -> 0.00
    }
}

private fun Double.truncateToFourDecimalPlacesNoRounding(): String {
    val str = this.toString()
    return if (str.contains(".")) {
        val decimalIndex = str.indexOf(".")
        val endIndex = decimalIndex + 5
        if (endIndex <= str.length) str.substring(0, endIndex) else str
    } else {
        str
    }
}
