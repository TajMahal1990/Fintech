package com.achievemeaalk.freedjf.deals


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


// -------------------- SCREEN --------------------
@Composable
fun DealsScreen(
    navController: NavHostController
) {
    val deals = remember {
        listOf(
            DealUi("1", "Transfer in", "0xA3f...92D", "12 Sep · 14:32", "+0.42 ETH", true),
            DealUi("2", "Transfer out", "0x91B...F21", "11 Sep · 19:05", "-0.18 ETH", false),
            DealUi("3", "Swap", "Uniswap", "10 Sep · 09:12", "USDT → ETH", true)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Payout",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(deals, key = { it.id }) { deal ->
                DealRow(deal)
            }
        }
    }
}


@Composable
fun DealRow(deal: DealUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Иконка (нейтральная)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF1F2226)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (deal.isIncome) "↗" else "↘",
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = deal.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = "${deal.from} · ${deal.dateTime}",
                    fontSize = 12.sp,
                    color = Color(0xFF8A8F98)
                )
            }

            Text(
                text = deal.amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(10.dp))

        // Divider — как в админках
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF1F2226))
        )
    }
}

// -------------------- MODEL --------------------

