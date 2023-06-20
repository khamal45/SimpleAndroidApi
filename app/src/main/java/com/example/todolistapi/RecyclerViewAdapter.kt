package com.example.todolistapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val context: Context ,val data1: ArrayList<ModelData>, val recyclerViewInterface: RecyclerViewInterface): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {



    class MyViewHolder(itemView: View, recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(itemView) {
        val tvnama:TextView = itemView.findViewById(R.id.tvnama)
        val tvtanggal:TextView = itemView.findViewById(R.id.tvtanggal)
        val tvjam:TextView = itemView.findViewById(R.id.tvjam)
        val abc: Unit = itemView.setOnClickListener {
            if (recyclerViewInterface != null){
    val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    recyclerViewInterface.onclick(position)
                }
            }
        }
        val btnedit: Unit = itemView.findViewById<ImageButton?>(R.id.editbtn).setOnClickListener {
            if (recyclerViewInterface != null){
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION){
                    recyclerViewInterface.oneditclick(position)
                }
            }
        }
        val btnhapus: Unit = itemView.findViewById<ImageButton?>(R.id.hapusbtn).setOnClickListener {
            if (recyclerViewInterface != null){
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION){
                    recyclerViewInterface.onhpsclick(position)
                }
            }
        }
        val checkbok = itemView.findViewById<CheckBox>(R.id.checkBox)
       val checkboxcek = checkbok.setOnClickListener{
            if (recyclerViewInterface != null) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onchecked(position, checkbok.isChecked)
                }
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
      val view:View = LayoutInflater.from(context)
   .inflate(R.layout.recycler_view_item,parent,false)
        return MyViewHolder(view,recyclerViewInterface)


    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
      holder.tvnama.setText(data1.get(position).nama)
        holder.tvtanggal.setText(data1.get(position).tanggal)
        holder.tvjam.setText(data1.get(position).jam)

        holder.checkbok.isChecked = data1.get(position).selesai > 0
        holder.abc
        holder.btnedit
        holder.btnhapus
        holder.checkboxcek
    }



    override fun getItemCount(): Int {
        return data1.size
    }
}