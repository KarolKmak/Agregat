package com.example.agregat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.agregat.log as log

class logowanie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logowanie)

        var username = findViewById(R.id.username) as EditText
        var password = findViewById(R.id.password) as EditText
        var LoginBtn = findViewById(R.id.BtnLogowanie) as Button

        LoginBtn.setOnClickListener {

            val usernameT = username.text;
            val passwordT = password.text;

            Toast.makeText(this@logowanie, usernameT, Toast.LENGTH_LONG).show()
        }
    }
}