package com.andromasters.weather.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.andromasters.weather.R
import com.andromasters.weather.presentation.theme.DeepBlue
import com.andromasters.weather.presentation.theme.WeatherAppTheme
import com.andromasters.weather.domain.location.LocationData
import com.andromasters.weather.domain.weather.WeatherData
import com.andromasters.weather.domain.weather.WeatherInfo
import com.andromasters.weather.domain.weather.WeatherType
import com.andromasters.weather.presentation.Screen
import com.andromasters.weather.presentation.forecast.WeatherState
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    navController: NavController?,
    resetForecastData: () -> Unit,
    modifier: Modifier = Modifier
) {

    var locationText by remember {
        mutableStateOf("Zmień miejscowość")
    }
    var locationName by remember {
        mutableStateOf("")
    }

    var isHintVisible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = state.weatherInfo?.locationData) {
        locationName = state.weatherInfo?.locationData?.name ?: ""
        delay(2000)
        isHintVisible = false
        delay(2000)
        locationText = locationName
        isHintVisible = true
    }

    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedVisibility(
                        visible = isHintVisible,
                        enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
                        exit = fadeOut(animationSpec = tween(durationMillis = 2000))
                    ) {
                        Text(
                            text = locationText,
                            color = Color.White,
                            modifier = Modifier
                                .border(1.dp, Color.White, shape = RoundedCornerShape(7.dp))
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    bottom = 5.dp,
                                    top = 5.dp
                                )
                                .clickable {
                                    resetForecastData()
                                    navController?.navigate(Screen.Search.name) {
                                        popUpTo(navController.graph.id) {
                                            saveState = true
                                        }
                                    }
                                }
                        )
                    }
                    Text(
                        text = "${stringResource(R.string.today_label)} ${
                            data.time?.format(
                                DateTimeFormatter.ofPattern("HH:mm")
                            )
                        }",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = WeatherType.fromWMO(data.weatherIcon).iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}°C",
                    fontSize = 50.sp,
                    color = data.temperatureCelsius?.let {
                        when {
                            it < 10.0 -> Color.Blue
                            it in 10.0 .. 20.0 -> Color.Black
                            else -> Color.Red
                        }
                    } ?: Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.weatherDesc ?: "",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = data.uvIndex ?: 0,
                        unit = " uv",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_uv_index),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.humidity?.roundToInt() ?: 0,
                        unit = "%",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.windSpeed?.roundToInt() ?: 0,
                        unit = data.windSpeedUnit ?: "",
                        icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun WeatherCarPreview() {
    WeatherAppTheme {
        WeatherCard(
            navController = null,
            resetForecastData = {},
            state = WeatherState(
                weatherInfo = WeatherInfo(
                    listOf(
                        WeatherData(
                            LocalDateTime.now(),
                            9.0, 1, 4.0, "km/h", 5.0, 1, "Przelotne opacy"
                        )
                    ),
                    WeatherData(
                        LocalDateTime.now(),
                        9.0, 2, 4.0, "km/h", 5.0, 2, "Pochmurno"
                    ),
                    LocationData("123455", "Kazimierz Dolny")
                )
            ),
            backgroundColor = DeepBlue
        )
    }
}