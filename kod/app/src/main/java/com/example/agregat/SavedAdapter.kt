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


class SavedAdapter(private val context: Context): RecyclerView.Adapter<SavedViewHolder>() {

    override fun getItemCount(): Int {
        val pref: SharedPreferences = context.getSharedPreferences("articles_table", Context.MODE_PRIVATE)
        return pref.getInt("table_size", 0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.article_row, parent, false)
        return SavedViewHolder(cellForRow, context)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val pref: SharedPreferences = context.getSharedPreferences("articles_table", Context.MODE_PRIVATE)
        holder.itemView.findViewById<TextView>(R.id.textView_article_title).text = pref.getString("title_"+(position+1), "error-SavedAdapter")
        holder.itemView.findViewById<TextView>(R.id.textView_article_description).text = pref.getString("desc_"+(position+1), "error-SavedAdapter")
        holder.url = pref.getString("url_"+(position+1), "")
        holder.itemView.findViewById<RatingBar>(R.id.ratingBar).rating = pref.getInt("imp_"+(position+1), 3).toFloat()
        holder.itemView.findViewById<RatingBar>(R.id.ratingBar).isEnabled = false

        //Zaznaczenie artykułów zapisanych na później
            //println(pref.getAll())
            holder.itemView.findViewById<CheckBox>(R.id.checkBox).isChecked = true

        //Obsługa przycisku zapisywania artykułu na później
        holder.itemView.findViewById<CheckBox>(R.id.checkBox).setOnClickListener{
            holder.save(
                holder.itemView.findViewById<TextView>(R.id.textView_article_title).text.toString()
            )
        }
    }


}

class SavedViewHolder(private val view: View, private val context: Context, var url:String? = null): RecyclerView.ViewHolder(view) {

    init {
        //otworzenie artykułu
        view.setOnClickListener {
            val intent = Intent(view.context, ArticlePage::class.java)
            intent.putExtra(CustomViewHolder.link, url)
            view.context.startActivity(intent)
        }
    }

    fun save(title: String = "") {
        val pref: SharedPreferences = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
        val table: SharedPreferences = context.getSharedPreferences("articles_table", Context.MODE_PRIVATE)
        var i = 1
        while (table.getString("title_$i", "") != title){
            i++
        }
        val editor = pref.edit()
        //Usunięcie artykułu z zakładki na później
        editor.remove("title_$title").apply()
        table(i)
        Toast.makeText(context, "Usunięto", Toast.LENGTH_SHORT).show()
    }
    private fun table(rozmiar: Int){
        val table: SharedPreferences = context.getSharedPreferences("articles_table", Context.MODE_PRIVATE)
        val editor = table.edit()
        if(rozmiar == table.getInt("table_size", 0)) {
            println("usunięto element numer: $rozmiar")
            editor.remove("title_$rozmiar").apply()
            editor.remove("desc_$rozmiar").apply()
            editor.remove("url_$rozmiar").apply()
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
            println("Usunięto w środku: $ostatni")
            editor.apply {
                putString("title_$rozmiar", tekst)
                putString("desc_$rozmiar", opis)
                putString("url_$rozmiar", adres)
                putInt("table_size", ostatni-1)
            }.apply()
            editor.remove("title_$ostatni").apply()
            editor.remove("desc_$ostatni").apply()
            editor.remove("url_$ostatni").apply()
            println("rozmiar tabeli po usunięciu: "+table.getInt("table_size", 0))
        }
    }
}