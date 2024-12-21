package com.example.tradingapp.compose.search

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tradingapp.R
import com.example.tradingapp.compose.home.ListSection
import com.example.tradingapp.compose.utils.SearchField

@Composable
fun SearchScreen(onCloseBottomSheet: (Boolean) -> Unit) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 36.dp)) {
                SearchField(
                    editable = true
                )
                Image(
                    painter = painterResource(R.drawable.close),
                    contentDescription = "Close",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterEnd)
                        .size(36.dp)
                        .clickable {
                            onCloseBottomSheet.invoke(true)
                        }
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            RecentSearches()
            ExploreSection()
            TopMarketCaps()
        }
    }
}


@Composable
private fun RecentSearches() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recents",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Clear",
                fontSize = 12.sp,
                color = Color(0xFF6771EA),
                modifier = Modifier.padding(top = 11.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RecentList(5)
        }
    }
}

@Composable
fun RecentList(count: Int) {
    LazyRow {
        items(count) { index ->
            RecentSearchItem(
                image = if (index % 3 == 0) R.drawable.chill_doge else if (index % 3 == 1) R.drawable.dino else R.drawable.bonk,
                name = "CB"
            )
        }
    }
}

@Composable
private fun RecentSearchItem(image: Int, name: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF6771EA),
                shape = RoundedCornerShape(22.dp),
            )
            .padding(horizontal = 11.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Coin",
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 9.dp)
        )
    }
}

@Composable
private fun ExploreSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Explore",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Reduced spacing
        ) {
            ExploreChip(
                text = "Top gain",
                iconRes = R.drawable.top_gain,
                isSelected = false
            )
            ExploreChip(
                text = "Filter",
                iconRes = R.drawable.filter,
                isSelected = true
            )
            ExploreChip(
                text = "New pairs",
                iconRes = R.drawable.new_pairs,
                isSelected = true
            )
        }
    }
}

@Composable
private fun ExploreChip(
    text: String,
    iconRes: Int,
    isSelected: Boolean
) {
    Row(
        modifier = Modifier
            .width(110.dp)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF6771EA) else Color(0xFF383838),
                shape = RoundedCornerShape(22.dp)
            )
            .background(
                if (isSelected) Color(0xFF6771EA) else Color.Transparent,
                RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 12.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF383838),
            modifier = Modifier.padding(start = 6.dp)
        )
    }
}

@Composable
private fun TopMarketCaps() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 31.dp)
    ) {
        Text(
            text = "Top Market Caps",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        ListSection(6) {}
    }
}
