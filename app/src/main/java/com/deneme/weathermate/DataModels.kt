package com.deneme.weathermate

data class WeatherResponse(
    val coord: Coordinates,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val name: String
)

data class Coordinates(val lon: Double, val lat: Double)
data class Weather(val id: Int, val main: String, val description: String, val icon: String)
data class Main(val temp: Double, val pressure: Int, val humidity: Int)
data class Wind(val speed: Double, val deg: Int)
data class Clouds(val all: Int)
data class Sys(val country: String, val sunrise: Long, val sunset: Long)
