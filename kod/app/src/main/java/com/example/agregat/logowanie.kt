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

        var username = findViewById<EditText>(R.id.username)
        var password = findViewById<EditText>(R.id.password)
        var loginBtn = findViewById<Button>(R.id.BtnLogowanie)

        loginBtn.setOnClickListener {

            val usernameT = username.text
            val passwordT = password.text


            //Toast.makeText(this@logowanie, usernameT, Toast.LENGTH_LONG).show()
       val bg = log(this);
            bg.execute(usernameT.toString(), passwordT.toString())
        }
    }
}