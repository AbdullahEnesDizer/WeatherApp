package com.deneme.weathermate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deneme.weathermate.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var nameofcity: String
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameofcity = intent.getStringExtra("CITY_NAME") ?: "DefaultCity"
        Log.d("FIRSTCityName", "FIRSTCity Name: $nameofcity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)




        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance().format(calendar.time)
        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

        findViewById<TextView>(R.id.day).text = dayOfWeek.toString()
        findViewById<TextView>(R.id.date).text = currentDate

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)
        var call: Call<WeatherResponse>

        var intent = intent
        if (intent != null && intent.hasExtra("cityname")) {
            val cityName = intent.getStringExtra("cityname")
            call = cityName?.let { weatherService.getCurrentWeatherDataByCity(it, "b55a61f39484dfd63052d388874cc038") }!!
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { weatherData ->
                            findViewById<TextView>(R.id.cityName).text = weatherData.name
                            findViewById<TextView>(R.id.windSpeed).text = weatherData.wind.toString().substring(11, 15)
                            findViewById<TextView>(R.id.humidity).text = weatherData.main.humidity.toString()
                            val temperatureInCelsius = (weatherData.main.temp - 273)
                            val formattedTemperature = String.format("%.2f°C", temperatureInCelsius.toDouble())
                            findViewById<TextView>(R.id.temp).text = formattedTemperature
                            findViewById<TextView>(R.id.condition).text = weatherData.weather.firstOrNull()?.main?:"unknown"
                            val sunriseTime = Date(weatherData.sys.sunrise * 1000)
                            val sunsetTime = Date(weatherData.sys.sunset * 1000)
                            findViewById<TextView>(R.id.weather).text = weatherData.weather.firstOrNull()?.main?:"unknown"
                            val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            findViewById<TextView>(R.id.sunRise).text = format.format(sunriseTime)
                            findViewById<TextView>(R.id.sunset).text = format.format(sunsetTime)

                            val condition = findViewById<TextView>(R.id.condition).text
                            changeImagsAccordingToWeaterCondition(condition)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                }
            })
        } else {


            Log.d("CityName_target", "CityName_target: $nameofcity")
            call = weatherService.getCurrentWeatherDataByCity(nameofcity, "b55a61f39484dfd63052d388874cc038")
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { weatherData ->
                            findViewById<TextView>(R.id.cityName).text = weatherData.name
                            findViewById<TextView>(R.id.windSpeed).text = weatherData.wind.toString().substring(11, 15)
                            findViewById<TextView>(R.id.humidity).text = weatherData.main.humidity.toString()
                            val temperatureInCelsius = (weatherData.main.temp - 273)
                            val formattedTemperature = String.format("%.2f°C", temperatureInCelsius.toDouble())
                            findViewById<TextView>(R.id.temp).text = formattedTemperature
                            findViewById<TextView>(R.id.condition).text = weatherData.weather.firstOrNull()?.main?:"unknown"
                            val sunriseTime = Date(weatherData.sys.sunrise * 1000)
                            val sunsetTime = Date(weatherData.sys.sunset * 1000)
                            findViewById<TextView>(R.id.weather).text = weatherData.weather.firstOrNull()?.main?:"unknown"
                            val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            findViewById<TextView>(R.id.sunRise).text = format.format(sunriseTime)
                            findViewById<TextView>(R.id.sunset).text = format.format(sunsetTime)

                            val condition = findViewById<TextView>(R.id.condition).text
                            changeImagsAccordingToWeaterCondition(condition)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                }
            })
        }

        var search = findViewById<EditText>(R.id.searchView)
        var searchBtn = findViewById<ImageButton>(R.id.searchBtn)
        searchBtn.setOnClickListener {
            val searchText = search.text.toString()
            call = weatherService.getCurrentWeatherDataByCity(searchText, "b55a61f39484dfd63052d388874cc038")
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { weatherData ->
                            findViewById<TextView>(R.id.cityName).text = weatherData.name
                            findViewById<TextView>(R.id.windSpeed).text = weatherData.wind.toString().substring(11, 15)
                            findViewById<TextView>(R.id.humidity).text = weatherData.main.humidity.toString()
                            val temperatureInCelsius = (weatherData.main.temp - 273)
                            val formattedTemperature = String.format("%.2f°C", temperatureInCelsius.toDouble())
                            findViewById<TextView>(R.id.temp).text = formattedTemperature
                            findViewById<TextView>(R.id.condition).text = weatherData.weather.firstOrNull()?.main?:"unknown"
                            val sunriseTime = Date(weatherData.sys.sunrise * 1000)
                            val sunsetTime = Date(weatherData.sys.sunset * 1000)
                            findViewById<TextView>(R.id.weather).text = weatherData.weather.firstOrNull()?.main?:"unknown"
                            val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
                            findViewById<TextView>(R.id.sunRise).text = format.format(sunriseTime)
                            findViewById<TextView>(R.id.sunset).text = format.format(sunsetTime)

                            val condition = findViewById<TextView>(R.id.condition).text
                            changeImagsAccordingToWeaterCondition(condition)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {

                }
            })
        }

        val favList = findViewById<Button>(R.id.favList)
        favList.setOnClickListener{
            intent = Intent(applicationContext,MainFavourites::class.java)
            startActivity(intent)
        }

        val favBtn = findViewById<Button>(R.id.favBtn)
        favBtn.setOnClickListener {
            val cityN = findViewById<TextView>(R.id.cityName).text

            var file = File(getExternalFilesDir(null), "fav_cities2.txt")


            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Fav list dosyası oluşturulamadı", Toast.LENGTH_SHORT).show()
                }
            }

            try {

                val existingCities = file.readLines()

                if (!existingCities.contains(cityN)) {
                    val writer = FileWriter(file, true) // 'true' if you want to append to the file
                    if(existingCities.isEmpty()){
                        writer.write("$cityN")
                    } else{
                        writer.write("\n$cityN")
                    }
                    writer.close()
                    Toast.makeText(applicationContext, "Favorilere eklendi", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Bu şehir zaten favorilerde", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Favorilere eklenmedi", Toast.LENGTH_SHORT).show()
            }
        }

    }





    private fun changeImagsAccordingToWeaterCondition(condition: CharSequence) {
        when (condition){
            "Clear Sky", "Sunny", "Clear" -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.root.setBackgroundResource(R.drawable.cloud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "light Rain", "Drizzle", "Moderate Rain", "Showers", "Rain" -> {
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> {
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }

        }
        binding.lottieAnimationView.playAnimation()

    }

}