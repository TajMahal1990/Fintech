package com.achievemeaalk.freedjf.ui.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// -------------------- SCREEN --------------------

@Composable
fun NotificationsScreen(
    navController: NavHostController
) {
    val notifications = remember {
        listOf(
            NotificationUi(
                id = "1",
                title = "Transaction confirmed",
                description = "Transfer 0.42 ETH confirmed",
                time = "2 min ago",
                type = NotificationType.SUCCESS
            ),
            NotificationUi(
                id = "2",
                title = "Withdrawal pending",
                description = "Waiting for network confirmation",
                time = "12 min ago",
                type = NotificationType.PENDING
            ),
            NotificationUi(
                id = "3",
                title = "Security alert",
                description = "New login from unknown device",
                time = "Yesterday",
                type = NotificationType.WARNING
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Notifications",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(notifications, key = { it.id }) { notification ->
                NotificationRow(notification)
            }
        }
    }
}

@Composable
fun NotificationRow(notification: NotificationUi) {
    val indicatorColor = when (notification.type) {
        NotificationType.SUCCESS -> Color(0xFF3DDC97)
        NotificationType.PENDING -> Color(0xFF8A8F98)
        NotificationType.WARNING -> Color(0xFFE5533D)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.Top
        ) {

            // Индикатор события
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(indicatorColor)
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = notification.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = notification.description,
                    fontSize = 13.sp,
                    color = Color(0xFF8A8F98)
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = notification.time,
                    fontSize = 11.sp,
                    color = Color(0xFF6E737B)
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        // Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF1F2226))
        )
    }
}

data class NotificationUi(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val type: NotificationType
)

enum class NotificationType {
    SUCCESS,
    PENDING,
    WARNING
}


