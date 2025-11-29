package com.achievemeaalk.freedjf.ui.onboarding


import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.achievemeaalk.freedjf.R
import com.achievemeaalk.freedjf.ui.settings.LanguageSettingsSheet
import com.achievemeaalk.freedjf.ui.settings.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetStartedScreen(
    onGetStartedClick: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var showLanguageSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

    if (showLanguageSheet) {
        ModalBottomSheet(
            onDismissRequest = { showLanguageSheet = false },
            sheetState = sheetState
        ) {
            LanguageSettingsSheet(viewModel = settingsViewModel)
        }
    }

    // -------------------- BACKGROUND --------------------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0C0F)) // premium dark crypto background
            .systemBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // -------------------- LANGUAGE BUTTON --------------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedVisibility(visible = visible) {
                    IconButton(onClick = {
                        scope.launch {
                            sheetState.show()
                            showLanguageSheet = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Language,
                            tint = Color(0xFF9BA2AE),
                            contentDescription = "",
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // -------------------- LOGO WITH CRYPTO GLOW --------------------
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(700)) + scaleIn(tween(700))
            ) {
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0x5514D3A5),
                                    Color(0x2214D3A5),
                                    Color.Transparent
                                ),
                                radius = 500f
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    // PANEL (transparent glass)
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(Color(0x1714D3A5)) // glass-like panel
                            .padding(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable._2),
                            contentDescription = "",
                            modifier = Modifier.size(110.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // -------------------- TITLE --------------------
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, 150)) + slideInVertically(tween(500, 150))
            ) {
                Text(
                    text = "Welcome to A5",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // -------------------- SUBTITLE --------------------
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, 200)) + slideInVertically(tween(500, 200))
            ) {
                Text(
                    text = "Fast. Secure. Built for Traders.",
                    color = Color(0xFF9BA2AE),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // -------------------- FEATURES --------------------
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600, 300))
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Bullet("Instant USDT transfers")
                    Bullet("Multi-layer security protection")
                    Bullet("Optimized for high-volume traders")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // -------------------- GET STARTED BUTTON --------------------
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500, 350)) + slideInVertically(
                    tween(500, 350),
                    initialOffsetY = { it })
            ) {
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF14D3A5)
                    )
                ) {
                    Text(
                        text = "Get Started",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(38.dp))
        }
    }
}

// -------------------- BULLET ROW --------------------
@Composable
private fun Bullet(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color(0xFF14D3A5), CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = Color(0xFF9BA2AE),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
