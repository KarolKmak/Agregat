package com.example.agregat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Saved : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        val recyclerViewSaved: RecyclerView = findViewById(R.id.recyclerView_saved)
        recyclerViewSaved.layoutManager = LinearLayoutManager(this)
        recyclerViewSaved.adapter = SavedAdapter(this@Saved)

        zmien()
        val navigationView: BottomNavigationView = findViewById(R.id.navigationView)
        navigationView.menu.getItem(2).isChecked = true
    }


    private fun zmien() {
        val navigationView: BottomNavigationView = findViewById(R.id.navigationView)
        navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val i = Intent(this@Saved, MainActivity::class.java)
                    startActivity(i)
                }
                R.id.agregat -> {
                    val i = Intent(this@Saved, Agregat::class.java)
                    startActivity(i)
                }
                R.id.zapisane -> {
                    val i = Intent(this@Saved, Saved::class.java)
                    startActivity(i)
                }
            }

            true
        }
    }


}