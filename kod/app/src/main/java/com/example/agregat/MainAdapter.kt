package com.example.agregat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class MainAdapter(private val homeFeed: Array<HomeFeed>, private val context: Context): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int{
        return homeFeed.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.article_row, parent, false)
        return CustomViewHolder(cellForRow, null, context)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val article = homeFeed[position]
        holder.itemView.findViewById<TextView>(R.id.textView_article_title).text = article.Title
        holder.itemView.findViewById<TextView>(R.id.textView_article_description).text = article.article_description
        holder.itemView.findViewById<RatingBar>(R.id.ratingBar).rating = article.Importance.toFloat()
        holder.itemView.findViewById<RatingBar>(R.id.ratingBar).isEnabled = false
        holder.url = article.Adress

        //Zaznaczenie artykułów zapisanych na później
        val pref: SharedPreferences = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
            if (pref.getString("title_${article.Title}", null) == article.Title) {
                holder.itemView.findViewById<CheckBox>(R.id.checkBox).isChecked = true
            }


        //Obsługa przycisku zapisywania artykułu na później
        holder.itemView.findViewById<CheckBox>(R.id.checkBox).setOnClickListener{
            if (holder.itemView.findViewById<CheckBox>(R.id.checkBox).isChecked) {
                holder.save(
                    article.Title,
                    holder.itemView.findViewById<CheckBox>(R.id.checkBox).isChecked,
                    article.article_description,
                    article.Adress,
                    article.Importance
                )
            }
            else{
                holder.save(article.Title, holder.itemView.findViewById<CheckBox>(R.id.checkBox).isChecked)
            }
        }
    }
}

class CustomViewHolder(private val view: View, var url:String? = null, private val context: Context): RecyclerView.ViewHolder(view) {
    companion object {
        const val link = "article_link"
    }

    init {
        //otworzenie artykułu
        view.setOnClickListener {
            val intent = Intent(view.context, ArticlePage::class.java)
            intent.putExtra(link, url)
            view.context.startActivity(intent)
        }
    }

    fun save(title: String, switch: Boolean?, desc: String? = "", url: String? = "", imp: Int = 0) {
        val pref: SharedPreferences = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
        val editor = pref.edit()
        if (switch != null && switch == true) {
            //Zapisanie artykułu na później
            editor.apply {
                putString("title_$title", title)
            }.apply()
            table(true, title, pref.all.size, desc, url, imp)
            Toast.makeText(context, "Zapisano $title", Toast.LENGTH_SHORT).show()
        }
        else {

            val table: SharedPreferences = context.getSharedPreferences("articles_table", Context.MODE_PRIVATE)
            var i = 1
            while (table.getString("title_$i", "") != title){
                i++
            }
            table(false, title, i)
            //Usunięcie artykułu z zakładki na później
            editor.remove("title_$title").apply()
            Toast.makeText(context, "Usunięto $title", Toast.LENGTH_SHORT).show()
        }
    }
    private fun table(boolean: Boolean, title: String, rozmiar: Int, desc: String? = "error-MainAdapter", url: String? = "error-MainAdapter", imp: Int = 0){
        val table: SharedPreferences = context.getSharedPreferences("articles_table", Context.MODE_PRIVATE)
        val editor = table.edit()
        if (boolean){
            //dodanie obiektu do tabeli
            println("pozycja: $rozmiar")
            editor.apply {
                putString("title_$rozmiar", title)
                putString("desc_$rozmiar", desc)
                putString("url_$rozmiar", url)
                putInt("imp_$rozmiar", imp)
                putInt("table_size", rozmiar)
            }.apply()
            println("rozmiar tabeli: "+table.all.size)
        }
        else
        {
            if(rozmiar == table.getInt("table_size", 0)) {
                println("usunięto element numer: $rozmiar")
                editor.remove("title_$rozmiar").apply()
                editor.remove("desc_$rozmiar").apply()
                editor.remove("url_$rozmiar").apply()
                editor.remove("imp_$rozmiar").apply()
                editor.apply {
                    putInt("table_size", table.getInt("table_size", 0) - 1)
                }.apply()
            }
            else
            {
                val ostatni = table.getInt("table_size", 0)
                val tekst = table.getString("title_$ostatni", "błąd")
                val opis = table.getString("desc_$ostatni", "błąd")
                val adres = table.getString("url_$ostatni", "błąd")
                val import = table.getInt("imp_$ostatni", 0)
                println("Usunięto w środku: $ostatni")
                editor.apply {
                    putString("title_$rozmiar", tekst)
                    putString("desc_$rozmiar", opis)
                    putString("url_$rozmiar", adres)
                    putInt("imp_$rozmiar", import)
                    putInt("table_size", ostatni-1)
                }.apply()
                editor.remove("title_$ostatni").apply()
                editor.remove("desc_$ostatni").apply()
                editor.remove("url_$ostatni").apply()
                editor.remove("imp_$ostatni").apply()
                println("rozmiar tabeli po usunięciu: "+table.getInt("table_size", 0))
            }
        }
    }


}

