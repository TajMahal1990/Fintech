package com.achievemeaalk.freedjf.ui.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.achievemeaalk.freedjf.R
import com.achievemeaalk.freedjf.ui.theme.Dimensions
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import com.achievemeaalk.freedjf.ui.components.AnimatedPrimaryButton
import com.achievemeaalk.freedjf.ui.theme.InactiveOnboardingIndicator
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import com.achievemeaalk.freedjf.ui.theme.Motion
import kotlinx.coroutines.delay
import androidx.compose.animation.slideInVertically
import androidx.compose.ui.graphics.Color

// ----------------------------------------------------------
// MODEL
// ----------------------------------------------------------
data class OnboardingItem(
    val titleResId: Int,
    val descriptionResId: Int,
    val lottieResId: Int
)

val onboardingPages = listOf(
    OnboardingItem(
        titleResId = R.string.onboarding_title_1,
        descriptionResId = R.string.onboarding_desc_1,
        lottieResId = R.raw.wallet
    ),
    OnboardingItem(
        titleResId = R.string.onboarding_title_2,
        descriptionResId = R.string.onboarding_desc_2,
        lottieResId = R.raw.transactions
    ),
    OnboardingItem(
        titleResId = R.string.onboarding_title_3,
        descriptionResId = R.string.onboarding_desc_3,
        lottieResId = R.raw.notification
    )
)

// ----------------------------------------------------------
// SCREEN
// ----------------------------------------------------------
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is OnboardingEvent.NavigateToName -> {
                    navController.navigate(firstAccountPromptRoute) {
                        popUpTo(onboardingRoute) { inclusive = true }
                    }
                }
                OnboardingEvent.NavigateToAccounts -> {
                    navController.navigate(firstAccountPromptRoute) {
                        popUpTo(onboardingRoute) { inclusive = true }
                    }
                }
                OnboardingEvent.NavigateToHome -> {
                    navController.navigate("dashboard") {
                        popUpTo(onboardingRoute) { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF0B0C0F)   // PREMIUM DARK BACKGROUND
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF0B0C0F))
        ) {

            TopSection(
                onSkipClick = { viewModel.onOnboardingFinished() }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPage(
                    item = onboardingPages[page],
                    pagerState = pagerState,
                    pageIndex = page
                )
            }

            BottomSection(
                pagerState = pagerState,
                onNextClick = {
                    scope.launch {
                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            viewModel.onOnboardingFinished()
                        }
                    }
                }
            )
        }
    }
}

// ----------------------------------------------------------
// TOP SECTION
// ----------------------------------------------------------
@Composable
fun TopSection(onSkipClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.screenPaddingHorizontal,
                vertical = Dimensions.spacingSmall
            )
    ) {
        Text(
            text = stringResource(R.string.onboarding_skip_button),
            color = Color(0xFF9BA2AE),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onSkipClick() }
                .padding(Dimensions.spacingSmall)
        )
    }
}

// ----------------------------------------------------------
// PAGE
// ----------------------------------------------------------
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPage(item: OnboardingItem, pagerState: PagerState, pageIndex: Int) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(item.lottieResId))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        visible = pagerState.currentPage == pageIndex
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0C0F))
            .padding(horizontal = Dimensions.spacingHuge)
            .pagerAnimation(pagerState, pageIndex)
    ) {

        // Lottie
        AnimatedVisibility(visible = visible, enter = fadeIn(tween(500))) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.spacingHuge))

        // Title
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, 150)) + slideInVertically(tween(400, 150))
        ) {
            Text(
                text = stringResource(item.titleResId),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.screenPadding))

        // Description
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, 250)) + slideInVertically(tween(400, 250))
        ) {
            Text(
                text = stringResource(item.descriptionResId),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF9BA2AE),
                textAlign = TextAlign.Center
            )
        }
    }
}

// ----------------------------------------------------------
// BOTTOM SECTION
// ----------------------------------------------------------
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomSection(pagerState: PagerState, onNextClick: () -> Unit) {

    val buttonText = if (pagerState.currentPage < onboardingPages.size - 1)
        R.string.onboarding_next_button
    else
        R.string.get_started_button

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.screenPaddingHorizontal,
                vertical = Dimensions.spacingExtraLarge
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        PageIndicator(
            pageCount = onboardingPages.size,
            currentPage = pagerState.currentPage
        )

        AnimatedPrimaryButton(
            onClick = onNextClick,
            shape = RoundedCornerShape(Dimensions.cornerRadiusExtraLarge),
            contentPadding = PaddingValues(Dimensions.screenPadding),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF14D3A5) // A5 accent
            )
        ) {
            Text(
                text = stringResource(buttonText),
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    }
}

// ----------------------------------------------------------
// INDICATOR
// ----------------------------------------------------------
@Composable
fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingSmall)) {
        repeat(pageCount) { index ->

            val isSelected = index == currentPage

            val color by animateColorAsState(
                targetValue = if (isSelected)
                    Color(0xFF14D3A5)   // A5 turquoise
                else
                    Color(0xFF2A2E33), // inactive dark
                animationSpec = tween(300)
            )

            val width by animateDpAsState(
                targetValue = if (isSelected) Dimensions.spacingExtraLarge else Dimensions.spacingMedium,
                animationSpec = tween(300)
            )

            Box(
                modifier = Modifier
                    .height(Dimensions.spacingMedium)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

// ----------------------------------------------------------
// PAGER ANIMATION
// ----------------------------------------------------------
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerAnimation(pagerState: PagerState, pageIndex: Int) = graphicsLayer {

    val pageOffset = ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue

    alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
    scaleX = lerp(0.8f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
    scaleY = lerp(0.8f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + (stop - start) * fraction
}
