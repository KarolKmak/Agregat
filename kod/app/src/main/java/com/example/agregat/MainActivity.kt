package com.example.agregat

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView_main: RecyclerView = findViewById(R.id.recyclerView_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //recyclerView_main.adapter = MainAdapter()

        fetchJson()
    }





    fun fetchJson()
    {
        val url = "http://192.168.1.10/app/index.php"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, Array<HomeFeed>::class.java)
                var recyclerView_main: RecyclerView = findViewById(R.id.recyclerView_main)
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Nie wiem jak żesz to zrobił, ale nie powinno cię tu być" + e.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}