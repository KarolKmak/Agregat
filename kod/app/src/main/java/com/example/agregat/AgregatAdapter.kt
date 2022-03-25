package com.example.agregat

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView


class AgregatAdapter(private val agregatFeed: Array<AgregatFeed>, private val context: Context): RecyclerView.Adapter<AgregatViewHolder>() {

    override fun getItemCount(): Int{
        return agregatFeed.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgregatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.agregat_row, parent, false)
        return AgregatViewHolder(cellForRow, context)
    }

    override fun onBindViewHolder(holder: AgregatViewHolder, position: Int) {
        val agregat = agregatFeed[position]
        holder.itemView.findViewById<TextView>(R.id.textView_article_title).text = agregat.Name
        holder.itemView.findViewById<TextView>(R.id.textView_article_description).text = agregat.Description
        val pref: SharedPreferences = context.getSharedPreferences("agregat", MODE_PRIVATE)
        holder.itemView.findViewById<RatingBar>(R.id.ratingBar).rating = pref.getInt("importance_"+agregat.Name, 0).toFloat()
        holder.itemView.findViewById<SwitchCompat>(R.id.switchAgregat).isChecked = pref.getBoolean("agregat_"+agregat.Name, false)
        holder.itemView.findViewById<SwitchCompat>(R.id.switchAgregat).setOnClickListener{
            holder.save(agregat.Name, holder.itemView.findViewById<SwitchCompat>(R.id.switchAgregat).isChecked, holder.itemView.findViewById<RatingBar>(R.id.ratingBar).rating.toInt())
        }
    }

}


class AgregatViewHolder(view: View, private val context: Context): RecyclerView.ViewHolder(view) {

    fun save(text: String, switch: Boolean?, imp: Int){
        val pref: SharedPreferences = context.getSharedPreferences("agregat", MODE_PRIVATE)
        val editor = pref.edit()
        if(switch != null && switch ==true) {
            editor.apply {
                putBoolean("agregat_$text", switch)
                putInt("importance_$text", imp)
            }.apply()
            table(true, text, pref.all.size, imp)
            Toast.makeText(context, "Zapisano$text$switch", Toast.LENGTH_SHORT).show()
        }
        else{
            val table: SharedPreferences = context.getSharedPreferences("agregat_table", MODE_PRIVATE)
            var i = 1
            while (table.getString("agregat_$i", "") != text){
                i++
            }
            editor.remove("agregat_$text").apply()
            editor.remove("importacne_$text").apply()
            table(false, text, i, 0)
            Toast.makeText(context, "Usunięto", Toast.LENGTH_SHORT).show()
        }
    }
    private fun table(boolean: Boolean, name: String, rozmiar: Int, imp: Int){
        val table: SharedPreferences = context.getSharedPreferences("agregat_table", MODE_PRIVATE)
        val editor = table.edit()
        if (boolean){
            //dodanie obiektu do tabeli
            println("pozycja: $rozmiar")
            editor.apply {
                putString("agregat_$rozmiar", name)
                putInt("importance_$rozmiar", imp)
                putInt("table_size", rozmiar)
            }.apply()
            println("rozmiar tabeli: "+table.all.size)
        }
        else
        {
            if(rozmiar == table.getInt("table_size", 1)) {
                println("usunięto element numer: $rozmiar")
                editor.remove("agregat_$rozmiar").apply()
                editor.remove("importance_$rozmiar").apply()
                editor.apply {
                    putInt("table_size", table.getInt("table_size", 0) - 1)
                }.apply()
            }
            else
            {
                val ostatni = table.getInt("table_size", 0)
                val tekst = table.getString("agregat_$ostatni", "błąd")
                val impor = table.getInt("importance_$ostatni", 0)
                println("Usunięto w środku: $ostatni")
                editor.apply {
                    putString("agregat_$rozmiar", tekst)
                    putInt("importance_$rozmiar", impor)
                    putInt("table_size", ostatni-1)
                }.apply()
                editor.remove("agregat_$ostatni").apply()
                editor.remove("importance_$ostatni").apply()
                println("rozmiar tabeli po usunięciu: "+table.getInt("table_size", 0))
            }
        }
    }
}