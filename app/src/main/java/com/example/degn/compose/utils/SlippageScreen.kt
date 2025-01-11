package com.example.degn.compose.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.degn.ui.theme.Purple
import com.example.degn.viewModels.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SlippageScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onDoneClick: (Boolean) -> Unit
) {
    var isManualSelected by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Slippage",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )
        Row {
            Text(
                text = if (isManualSelected) "Manual: ${viewModel.slippageValue.collectAsState().value.toInt()}%" else "Manual",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (!isManualSelected) Color.White else Purple)
                    .clickable { isManualSelected = true }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = if (!isManualSelected) Color.Black else Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Auto",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (isManualSelected) Color.White else Purple)
                    .clickable { isManualSelected = false }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = if (isManualSelected) Color.Black else Color.White
            )
        }
    }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Set the max price change during your trade.",
            color = Color.Gray
        )
        if (isManualSelected) {
            Slider(
                value = viewModel.slippageValue.collectAsState().value,
                onValueChange = { viewModel.setSlippageValue(it) },
                valueRange = 1f..50f,
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = SliderDefaults.colors(thumbColor = Purple, activeTrackColor = Purple)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CustomizedButton(text = "Done", paddingBottom = 4, icon = null) {
            onDoneClick.invoke(true)
        }
    }
}