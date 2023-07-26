package com.andromasters.weather.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val BENDING_FACTOR = 0.3f
private const val ROTATION_FACTOR = 10f

@Composable
fun BendingText(text: String, bendingStrength: Float) {
    var bendingOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(bendingStrength) {
        bendingOffset = bendingStrength
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .graphicsLayer(
                    translationX = bendingOffset,
                    rotationZ = bendingOffset * ROTATION_FACTOR // Obróć tekst w zależności od wartości bendingStrength
                )
                .scale(
                    scaleX = 1 - bendingOffset * BENDING_FACTOR,
                    scaleY = 1 - bendingOffset * BENDING_FACTOR
                )
                .padding(16.dp)
        )
    }
}