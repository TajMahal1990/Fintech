package com.achievemeaalk.freedjf.ui.system

import androidx.compose.runtime.Composable


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SystemScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "System status",
            style = MaterialTheme.typography.headlineMedium
        )

        SystemStatusCard(
            title = "API key",
            value = "Connected",
            icon = Icons.Default.VpnKey,
            statusColor = Color(0xFF4CAF50)
        )

        SystemStatusCard(
            title = "Internet",
            value = "Wi-Fi connected",
            icon = Icons.Default.Wifi,
            statusColor = Color(0xFF4CAF50)
        )

        SystemStatusCard(
            title = "Battery",
            value = "78%",
            icon = Icons.Default.BatteryFull,
            statusColor = Color(0xFF4CAF50)
        )

        SystemStatusCard(
            title = "Charging",
            value = "Not charging",
            icon = Icons.Default.Power,
            statusColor = Color(0xFFFF9800)
        )

        SystemStatusCard(
            title = "Permissions",
            value = "All granted",
            icon = Icons.Default.Security,
            statusColor = Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(8.dp))


    }
}

@Composable
fun SystemStatusCard(
    title: String,
    value: String,
    icon: ImageVector,
    statusColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = statusColor,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = statusColor
                )
            }
        }
    }
}

