package com.example.savemyposition

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class SaveDataAdapter : RecyclerView.Adapter<Holder>() {
    var listData = mutableListOf<Postion>()

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val positiondb = listData.get(position)
        holder.bind(positiondb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item,parent,false)
        return Holder(view)
    }

}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val time = itemView.findViewById<TextView>(R.id.time_item)
    val latitude: TextView = itemView.findViewById<TextView>(R.id.latitude_item)
    val longitude = itemView.findViewById<TextView>(R.id.longitude_item)

    fun bind(position: Postion){
        time?.text = position.Time
        latitude.text = position.latitude
        longitude?.text = position.longitude
    }
}