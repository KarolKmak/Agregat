package com.example.agregat

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity


class AgregatAdapter(val homeFeed: Array<HomeFeed>): RecyclerView.Adapter<AgregatViewHolder>() {

    override fun getItemCount(): Int{
        return homeFeed.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgregatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.article_row, parent, false)
        return AgregatViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: AgregatViewHolder, position: Int) {
        val article = homeFeed.get(position)
        holder.itemView.findViewById<TextView>(R.id.textView_article_title).text = article.Title
        holder.itemView.findViewById<TextView>(R.id.textView_article_description).text = article.article_description
        holder?.url = article.Adress
    }
}


class AgregatViewHolder(val view: View, var url:String? = null): RecyclerView.ViewHolder(view) {
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