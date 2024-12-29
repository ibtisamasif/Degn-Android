package com.example.tradingapp.compose.export

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.notification.TitleAndDescription
import com.example.tradingapp.compose.utils.BottomSheet
import com.example.tradingapp.compose.utils.Title
import com.example.tradingapp.ui.theme.Grey
import com.example.tradingapp.viewModels.export.ExportViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExportKeysScreen(
    viewModel: ExportViewModel = koinViewModel(),
    onButtonPress: (String) -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 45.dp)
            ) {
                Title("Export keys") { onButtonPress.invoke("Back") }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TitleAndDescription(
                "Your Wallets",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tristique vehicula purus."
            )
            Spacer(modifier = Modifier.height(16.dp))
            WalletCard(name = "Solana", key = "${viewModel.walletKey.take(4)}....${viewModel.walletKey.takeLast(4)}")
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Advanced",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            SecretPhraseCard{
                onButtonPress.invoke(it)
                viewModel.sendOtp()
            }
        }

        if (viewModel.isShowSecretKey.value) BottomSheet("SecretKey"){
           if(it) viewModel.isShowSecretKey.value = false
        }
    }
}

@Composable
fun WalletCard(name: String, key: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(45.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.solana),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "$name:",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Clip
        )
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key,
                fontSize = 14.sp,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.copy),
                contentDescription = null,
                modifier = Modifier.size(17.dp)
            )
        }
    }
}

@Composable
fun SecretPhraseCard(onButtonPress: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(45.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(18.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onButtonPress.invoke("SecretKey")},
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.lock),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Secret phrase",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Export",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .padding(top = 2.dp),
            tint = Color.Gray
        )
    }
}
