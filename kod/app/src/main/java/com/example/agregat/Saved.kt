package com.example.agregat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class Saved : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        zmien()
        val navigationView: BottomNavigationView = findViewById(R.id.navigationView)
        navigationView.getMenu().getItem(2).setChecked(true);
    }


    fun zmien() {
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