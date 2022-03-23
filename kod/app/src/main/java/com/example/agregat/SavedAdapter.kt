package com.example.agregat

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

class SavedViewHolder(val view: View, private val context: Context): RecyclerView.ViewHolder(view) {

    fun save(title: String = "") {
        val pref: SharedPreferences = context.getSharedPreferences("articles", Context.MODE_PRIVATE)
        val editor = pref.edit()
        //Usunięcie artykułu z zakładki na później
        editor.remove("title_$title").apply()
        editor.remove("url_$title").apply()
        editor.remove("titleDescription_$title").apply()
        Toast.makeText(context, "Usunięto", Toast.LENGTH_SHORT).show()
    }
}