package com.deneme.weathermate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class MainFavourites : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<FavCity>
    private var favCityList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favourites)


        val externalFile = File(getExternalFilesDir(null), "fav_cities2.txt")

        try {
            val reader = BufferedReader(FileReader(externalFile))

            favCityList.clear()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val favCity = FavCity(line.orEmpty())
                favCityList.add(favCity.cityname)
            }

            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        newRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        newRecyclerView.layoutManager = layoutManager
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()

        getCity()
    }

    private fun getCity() {
        val size = favCityList.size
        for (i in 0 until size) {
            val city = FavCity(favCityList[i])
            newArrayList.add(city)
        }
        newRecyclerView.adapter = MyAdapter(newArrayList)
    }
}
