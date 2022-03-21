package com.example.agregat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class Agregat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregat)

        var recyclerView_agregat: RecyclerView = findViewById(R.id.recyclerView_agregat)
        recyclerView_agregat.layoutManager = LinearLayoutManager(this)

        fetchJson()

        zmien()
        val navigationView: BottomNavigationView = findViewById(R.id.navigationView)
        navigationView.getMenu().getItem(1).setChecked(true);
    }
    fun zmien()
    {
        val navigationView: BottomNavigationView = findViewById(R.id.navigationView)
        navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val i = Intent(this@Agregat, MainActivity::class.java)
                    startActivity(i)
                }
                R.id.agregat -> {
                    val i = Intent(this@Agregat, Agregat::class.java)
                    startActivity(i)
                }
                R.id.zapisane -> {
                    val i = Intent(this@Agregat, Saved::class.java)
                    startActivity(i)
                }
            }

            true
        }
    }

    fun fetchJson()
    {
        val url = "http://192.168.1.10/app/agregat.php"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()
                val agregatFeed = gson.fromJson(body, Array<AgregatFeed>::class.java)
                var recyclerView_agregat: RecyclerView = findViewById(R.id.recyclerView_agregat)
                val context: Context = this@Agregat
                runOnUiThread {
                    recyclerView_agregat.adapter = AgregatAdapter(agregatFeed, context)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext,e.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}