package com.andromasters.weather.presentation.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andromasters.weather.presentation.component.WeatherCard
import com.andromasters.weather.presentation.component.WeatherForecast
import com.andromasters.weather.presentation.theme.DarkBlue
import com.andromasters.weather.presentation.theme.DeepBlue

@Composable
fun WeatherScreen(
    locationId: String,
    locationName: String,
    navController: NavController,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.loadWeatherInfo(locationId, locationName)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
        ) {
            WeatherCard(
                navController = navController,
                state = viewModel.state,
                resetForecastData = viewModel::resetForecastData,
                backgroundColor = DeepBlue
            )
            Spacer(modifier = Modifier.height(16.dp))
            WeatherForecast(
                state = viewModel.state
            )
        }
        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}