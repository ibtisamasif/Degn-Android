package com.example.tradingapp.compose.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.utils.BottomSheet
import com.example.tradingapp.compose.utils.ConfirmationButton
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.Purple

@Composable
fun PaymentScreen(title: String, onCloseBottomSheet: (Boolean) -> Unit) {
    var amount by remember { mutableStateOf("0.00") }
    var screenName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Title(title = title, isBottomSheet = true) {
            onCloseBottomSheet.invoke(true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                    .padding(1.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(21.5.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { screenName = "CashMini" }
                ) {
                    Text(
                        text = "Cash:",
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "$0.00",
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

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "$$amount", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

            if (title != "Send" && title != "Buy" && title != "Sell") {
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                        .padding(1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(21.5.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable { screenName = "DepositMini" }
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
                            contentDescription = "cash",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            var selectedPercentage by remember { mutableStateOf("25%") }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp, vertical = 32.dp)
            ) {
                listOf("10%", "25%", "50%", "MAX").forEachIndexed { _, label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .size(24.dp)
                            .background(if (selectedPercentage == label) Purple else Color.White)
                            .clickable { selectedPercentage = label }
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp),
                            color = if (selectedPercentage == label) Color.White else Color.Black,
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        NumericKeypad(onKeyPress = {key->
            amount = updateAmount(amount, key)
        })

        Row(
            modifier = Modifier
                .weight(0.8f)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ConfirmationButton{
                onCloseBottomSheet.invoke(true)
            }
        }

    }
    if(screenName != "") BottomSheet(screenName){
        screenName = ""
    }
}

@Composable
fun NumericKeypad(onKeyPress: (String) -> Unit) {
    val keys = listOf(
        listOf("1", "2", "3"), listOf("4", "5", "6"), listOf("7", "8", "9"), listOf(".", "0", "")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
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
                        if (key !== "") {
                            Text(text = key, style = MaterialTheme.typography.titleLarge)
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.clear),
                                contentDescription = "Clear",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun updateAmount(currentAmount: String, key: String): String {
    return when (key) {
        "" -> {
            if (currentAmount.isNotEmpty()) currentAmount.dropLast(1).ifEmpty { "0" } else "0"
        }
        "." -> {
            if (currentAmount.contains(".")) {
                currentAmount.replace(".", "") + "."
            } else {
                "$currentAmount."
            }
        }
        else -> {
            if (currentAmount == "0.00") key else currentAmount + key
        }
    }.trimStart('0').ifEmpty { "0.00" }
}

