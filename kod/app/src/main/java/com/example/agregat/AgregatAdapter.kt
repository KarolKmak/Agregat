package com.example.agregat

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.itemView.findViewById<SwitchCompat>(R.id.switchAgregat).isChecked = pref.getBoolean("Agregat_"+agregat.Name, false)
        holder.itemView.findViewById<SwitchCompat>(R.id.switchAgregat).setOnClickListener{
            holder.save(agregat.Name, holder.itemView.findViewById<SwitchCompat>(R.id.switchAgregat).isChecked)
        }
    }

}


class AgregatViewHolder(view: View, private val context: Context): RecyclerView.ViewHolder(view) {

    fun save(text: String, switch: Boolean?){
        val pref: SharedPreferences = context.getSharedPreferences("agregat", MODE_PRIVATE)
        val editor = pref.edit()
        if(switch != null && switch ==true) {
            editor.apply {
                putBoolean("Agregat_$text", switch)
            }.apply()
            Toast.makeText(context, "Zapisano$text$switch", Toast.LENGTH_SHORT).show()
        }
        else{
            editor.remove("Agregat_$text").apply()
            Toast.makeText(context, "Usunięto", Toast.LENGTH_SHORT).show()
        }
    }
}