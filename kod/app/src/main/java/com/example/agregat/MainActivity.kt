package com.example.agregat

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewMain: RecyclerView = findViewById(R.id.recyclerView_main)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)

        fetchJson()
        zmien()
    }



    private fun zmien()
    {
        val navigationView: BottomNavigationView = findViewById(R.id.navigationView)
        navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val i = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(i)
                }
                R.id.agregat -> {
                    val i = Intent(this@MainActivity, Agregat::class.java)
                    startActivity(i)
                }
                R.id.zapisane -> {
                    val i = Intent(this@MainActivity, Saved::class.java)
                    startActivity(i)
                }
            }

            true
        }
    }



    private fun fetchJson()
    {
        val table: SharedPreferences = this.getSharedPreferences("agregat_table", MODE_PRIVATE)
        var agregaty = ""
        var i = table.getInt("table_size", 0)
        while (i > 0){
            agregaty += "`agregat_name` LIKE \""+(table.getString("agregat_$i", "").toString())+"\""
            i--
            if (i > 0){agregaty+= " OR "}
        }
        println("Warunek: "+agregaty)

        val formBody:RequestBody = FormBody.Builder()
            .add("agregat", agregaty).build()
        val url = "http://192.168.1.10/app/index.php"
        val request = Request.Builder().url(url).post(formBody).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, Array<HomeFeed>::class.java)
                val recyclerViewMain: RecyclerView = findViewById(R.id.recyclerView_main)
                runOnUiThread {
                    recyclerViewMain.adapter = MainAdapter(homeFeed, this@MainActivity)
                }
            }


            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}