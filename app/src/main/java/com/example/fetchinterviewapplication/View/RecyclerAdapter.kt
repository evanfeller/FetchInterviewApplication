package com.example.fetchinterviewapplication.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchinterviewapplication.DataLayer.Room.ListEntity
import com.example.fetchinterviewapplication.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var itemArrayList: List<ListEntity> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.id_tv)
        var itemID: TextView = itemView.findViewById(R.id.itemID_tv)
        var name: TextView = itemView.findViewById(R.id.name_tv)

        init {
            itemView.setOnClickListener {
                println(id.text)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemArrayList[position].name
        holder.id.text = itemArrayList[position].id.toString()
        holder.itemID.text = itemArrayList[position].listID.toString()
    }

    override fun getItemCount(): Int {
        return itemArrayList.count()
    }

    fun updateItemList(itemList: List<ListEntity>) {
        itemArrayList = ArrayList()
        itemArrayList = itemList
        notifyDataSetChanged()
    }
}