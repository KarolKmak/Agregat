package com.example.agregat

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ArticlePage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        var articleView: WebView = findViewById(R.id.Article_view)
        val articleLink = intent.getStringExtra(CustomViewHolder.link)

        articleView.settings.javaScriptEnabled = true
        articleView.settings.loadWithOverviewMode = true
        articleView.settings.useWideViewPort = true
        if (articleLink != null) {
            articleView.loadUrl(articleLink)
        }
    }
}