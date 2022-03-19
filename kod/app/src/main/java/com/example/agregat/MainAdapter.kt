package com.example.agregat

import android.content.Intent
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class MainAdapter(val homeFeed: Array<HomeFeed>): RecyclerView.Adapter<SavedViewHolder>() {

    override fun getItemCount(): Int{
        return homeFeed.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.article_row, parent, false)
        return SavedViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val article = homeFeed.get(position)
        holder.itemView.findViewById<TextView>(R.id.textView_article_title).text = article.Title
        holder.itemView.findViewById<TextView>(R.id.textView_article_description).text = article.article_description
        holder?.url = article.Adress
    }
}

class CustomViewHolder(val view: View, var url:String? = null): RecyclerView.ViewHolder(view) {
    companion object{
        val link = "article_link"
    }

    init {
        view.setOnClickListener {
            val intent = Intent(view.context, ArticlePage::class.java)
            intent.putExtra(link,url)
            view.context.startActivity(intent)
        }
    }
}