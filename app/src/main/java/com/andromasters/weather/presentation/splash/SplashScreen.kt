package com.andromasters.weather.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.andromasters.weather.R
import com.andromasters.weather.presentation.Screen
import com.andromasters.weather.presentation.component.BendingText
import com.andromasters.weather.presentation.theme.DarkBlue
import com.andromasters.weather.BuildConfig
import kotlinx.coroutines.delay

enum class AnimationState {
    START,
    END
}

private const val ANIMATION_DURATION = 1200

@Composable
fun SplashScreen(
    navController: NavController
) {
    var scaleState by remember {
        mutableStateOf(AnimationState.START)
    }

    val scaleFactor = remember {
        Animatable(0f)
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.animated_weather)
    )
    val logoAnimationState =
        animateLottieCompositionAsState(composition = composition)


    LaunchedEffect(key1 = scaleState) {
        if (scaleState == AnimationState.START) {
            scaleFactor.animateTo(
                targetValue = 1f,
                animationSpec = tween(ANIMATION_DURATION, easing = LinearOutSlowInEasing)
            )
            scaleState = AnimationState.END
        } else {
            scaleFactor.animateTo(
                targetValue = 0f,
                animationSpec = tween(ANIMATION_DURATION, easing = LinearOutSlowInEasing)
            )
            delay(ANIMATION_DURATION.toLong())
            navController.navigate(Screen.Search.name) {
                popUpTo(Screen.Splash.name) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        Modifier
            .background(DarkBlue)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier.align(Alignment.TopCenter),
                contentAlignment = Alignment.BottomCenter
            ) {

                LottieAnimation(
                    composition = composition,
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    progress = { logoAnimationState.progress }
                )

                BendingText(
                    text = stringResource(R.string.app_name),
                    bendingStrength = scaleFactor.value
                )

                Text(
                    text = "Version : ${BuildConfig.VERSION_NAME}",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}