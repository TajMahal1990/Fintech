// com/achievemeaalk/freedjf/ui/components/BottomNav.kt
package com.achievemeaalk.freedjf.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.achievemeaalk.freedjf.R
import com.achievemeaalk.freedjf.ui.theme.Dimensions
import com.achievemeaalk.freedjf.ui.theme.OnSurface70
import com.achievemeaalk.freedjf.ui.theme.OnSurface80
import com.achievemeaalk.freedjf.ui.theme.headlineSmallBold
import com.achievemeaalk.freedjf.ui.theme.labelSmallSemiBold
import com.canopas.lib.showcase.IntroShowcaseScope

// -----------------------------
// BottomNavItem
// -----------------------------

sealed class BottomNavItem(
    val route: String,
    @StringRes val title: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Dashboard : BottomNavItem(
        "dashboard",
        R.string.dashboard,
        Icons.Outlined.Dashboard,
        Icons.Filled.Dashboard
    )

    object Transactions : BottomNavItem(
        "transactions",
        R.string.nav_transactions,
        Icons.Outlined.ReceiptLong,
        Icons.Filled.ReceiptLong
    )

    object Payouts : BottomNavItem(
        "payouts",
        R.string.nav_payouts,
        Icons.Outlined.Payments,
        Icons.Filled.Payments
    )

    object Notifications : BottomNavItem(
        "notifications",
        R.string.notifications,
        Icons.Outlined.Notifications,
        Icons.Filled.Notifications
    )

    object System : BottomNavItem(
        "system",
        R.string.nav_system,
        Icons.Outlined.Settings,
        Icons.Filled.Settings
    )
}


// -----------------------------
// Bottom Navigation Bar
// -----------------------------

@Composable
fun CustomBottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    items: List<BottomNavItem>,
    barHeight: Dp = Dimensions.bottomNavigationHeight
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Column(modifier = modifier.fillMaxWidth()) {

        Divider(color = MaterialTheme.colorScheme.outlineVariant)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .background(MaterialTheme.colorScheme.surface),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { screen ->
                BottomBarItem(
                    screen = screen,
                    isSelected = currentDestination?.hierarchy?.any { destination ->
                        val route = destination.route ?: return@any false
                        route == screen.route || route.startsWith(screen.route)
                    } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}



// -----------------------------
// BottomBarItem
// -----------------------------

@Composable
fun RowScope.BottomBarItem(
    screen: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val iconColor =
        if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant

    val textColor = iconColor

    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "icon_scale"
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
            .padding(vertical = Dimensions.spacingExtraSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
            contentDescription = stringResource(id = screen.title),
            tint = iconColor,
            modifier = Modifier
                .size(Dimensions.iconSizeMedium)
                .graphicsLayer(
                    scaleX = iconScale,
                    scaleY = iconScale
                )
        )

        Spacer(modifier = Modifier.height(Dimensions.spacingExtraSmall))

        Text(
            text = stringResource(id = screen.title),
            style = if (isSelected)
                MaterialTheme.typography.labelSmallSemiBold
            else
                MaterialTheme.typography.labelSmall,
            color = textColor,
            maxLines = 1
        )
    }
}
