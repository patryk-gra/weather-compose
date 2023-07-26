package com.andromasters.weather.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andromasters.weather.R
import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.presentation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val keyboardPattern = remember { Regex("[\\p{L}\\s]*") }
    val locations by viewModel.locations.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = viewModel.currentWeatherInfo) {
        viewModel.currentWeatherInfo.locationData?.let {
            navigateToForecastScreen(navController, it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = viewModel.state.searchText ?: "",
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            onValueChange = {
                if (it.matches(keyboardPattern)) {
                    viewModel.onSearchTextChange(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = stringResource(R.string.search_place_hint)) },
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (viewModel.state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else if (viewModel.state.error != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = viewModel.state.error ?: "",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(locations ?: emptyList()) { location ->
                    Text(
                        text = location.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .clickable {
                                keyboardController?.hide()
                                navigateToForecastScreen(navController, location)
                            }
                    )
                }
            }
        }
    }
}

fun navigateToForecastScreen(navController: NavController, location: LocationData) {
    navController.navigate(Screen.Forecast.name + "/${location.locationId}/${location.name}") {
        popUpTo(Screen.Search.name) {
            inclusive = true
        }
    }
}