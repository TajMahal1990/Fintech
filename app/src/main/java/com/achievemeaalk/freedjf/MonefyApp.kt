package com.achievemeaalk.freedjf


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.achievemeaalk.freedjf.ui.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.achievemeaalk.freedjf.ui.onboarding.firstAccountPromptRoute
import com.achievemeaalk.freedjf.ui.onboarding.onboardingRoute
import com.achievemeaalk.freedjf.ui.accounts.AccountsViewModel
import com.achievemeaalk.freedjf.ui.components.AddRecordBottomSheet
import com.achievemeaalk.freedjf.ui.components.BottomNavItem
import com.achievemeaalk.freedjf.ui.components.CustomBottomNavigationBar
import com.achievemeaalk.freedjf.ui.dashboard.DashboardViewModel
import com.achievemeaalk.freedjf.ui.settings.SettingsViewModel
import com.canopas.lib.showcase.IntroShowcase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonefyApp(
    navController: NavHostController = rememberNavController()
) {
    // ViewModels
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val accountsViewModel: AccountsViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    val isLoading by settingsViewModel.isLoading.collectAsState()
    val completedShowcaseRoutes by settingsViewModel.completedShowcaseRoutes.collectAsState()
    val language by settingsViewModel.language.collectAsState()
    val onboardingCompleted by settingsViewModel.onboardingCompleted.collectAsState()
    val getStartedCompleted by settingsViewModel.getStartedCompleted.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Start destination — фиксируется один раз
    val startDestination = remember {
        when {
            onboardingCompleted -> "dashboard"
            getStartedCompleted -> onboardingRoute
            else -> "get_started"
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val shouldShowMainAppUi =
        startDestination == "dashboard" ||
                (currentRoute != "get_started" && currentRoute != onboardingRoute)

    // Showcase — оставляем как есть
    val showcaseRoutes = remember {
        setOf("dashboard", "transactions", "payouts", "notifications", "system")
    }

    var showShowcase by remember { mutableStateOf(false) }
    var showcaseKey by remember { mutableStateOf("") }

    LaunchedEffect(currentRoute, completedShowcaseRoutes) {
        val baseRoute = showcaseRoutes.find { currentRoute?.startsWith(it) == true }
        if (baseRoute != null && !completedShowcaseRoutes.contains(baseRoute)) {
            showcaseKey = baseRoute
            showShowcase = true
        } else {
            showShowcase = false
        }
    }

    IntroShowcase(
        showIntroShowCase = showShowcase,
        onShowCaseCompleted = {
            if (showcaseKey.isNotBlank()) {
                settingsViewModel.addCompletedShowcaseRoute(showcaseKey)
                showcaseKey = ""
            }
            showShowcase = false
        }
    ) {
        if (shouldShowMainAppUi) {
            Scaffold(
                bottomBar = {
                    val shouldShowBottomBar = when (currentRoute) {
                        "get_started",
                        onboardingRoute,
                        firstAccountPromptRoute,
                        "first_account_success",
                        "paywall",
                        "passcode",
                        "setPasscode",
                        "receiptScanner",
                        "settings",
                        "securitySettings",
                        "homeScreenSettings",
                        "recurringBills"
                            -> false

                        else -> true
                    }

                    if (shouldShowBottomBar) {
                        CustomBottomNavigationBar(
                            navController = navController,
                            items = listOf(
                                BottomNavItem.Dashboard,
                                BottomNavItem.Transactions,
                                BottomNavItem.Payouts,
                                BottomNavItem.Notifications,
                                BottomNavItem.System
                            )
                        )


                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Navigation(
                        navController = navController,
                        dashboardViewModel = dashboardViewModel,
                        language = language,
                        startDestination = startDestination
                    )
                }
            }
        } else {
            Navigation(
                navController = navController,
                dashboardViewModel = dashboardViewModel,
                language = language,
                startDestination = startDestination
            )
        }
    }
}
