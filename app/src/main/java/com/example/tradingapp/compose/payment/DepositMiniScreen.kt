package com.example.tradingapp.compose.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.data.PaymentMethod
import com.example.tradingapp.ui.theme.Purple

@Composable
fun DepositMiniScreen() {
    var selectedMethod by remember { mutableStateOf(PaymentMethod.APPLE_PAY) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        PaymentOptionRow(
            label = "Apple Pay",
            icon = painterResource(R.drawable.apple_pay),
            isSelected = (selectedMethod == PaymentMethod.APPLE_PAY),
            isEnabled = true,
            onClick = {
                selectedMethod = PaymentMethod.APPLE_PAY
            }
        )

        PaymentOptionRow(
            label = "Credit/Debit Card",
            icon = painterResource(R.drawable.card),
            isSelected = (selectedMethod == PaymentMethod.CREDIT_DEBIT),
            isEnabled = true,
            onClick = {
                selectedMethod = PaymentMethod.CREDIT_DEBIT
            }
        )

        PaymentOptionRow(
            label = "Bank Transfer",
            icon = painterResource(R.drawable.bank_transfer),
            isEnabled = false,
            comingSoon = true
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun PaymentOptionRow(
    label: String,
    icon: Painter,
    isSelected: Boolean = false,
    isEnabled: Boolean = true,
    comingSoon: Boolean = false,
    onClick: () -> Unit = {}
) {
    val backgroundColor = if (isSelected && isEnabled) Color(0xFFEAEAEA) else Color.Transparent

    val rowModifier = if (isEnabled) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Row(
        modifier = rowModifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: icon + label
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                // Grey tint if disabled, otherwise black
                tint = if (isEnabled) Color.Black else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                color = if (isEnabled) Color.Black else Color.Gray,
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 16.sp)
            )
        }

        when {
            isEnabled && isSelected -> {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(color = Purple, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            !isEnabled && comingSoon -> {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Coming soon",
                    )
                }
            }
        }
    }
}