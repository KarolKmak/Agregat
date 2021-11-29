package com.example.agregat

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.agregat.databinding.ActivityScrollingBinding
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button


class ScrollingActivity : AppCompatActivity() {

private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityScrollingBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        //dzialajacy przycisk
        binding.przycisk.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.stackoverflow.com"))
            startActivity(intent)
        }
        //koniec dzialajacego przycisku


        //przycisk glebiej !!!dziala!!!
        val clickListener = View.OnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.stackoverflow.com"))
            startActivity(intent)
        }
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(clickListener)
        //koniec przycisku

        //logowanie
        val logowanieListener = View.OnClickListener {
            val intent = Intent(this@ScrollingActivity, logowanie::class.java)
            startActivity(intent)
        }
        val logowanie = findViewById<Button>(R.id.logowanie)
        logowanie.setOnClickListener(logowanieListener)
        //koniec logowania
    }
override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}