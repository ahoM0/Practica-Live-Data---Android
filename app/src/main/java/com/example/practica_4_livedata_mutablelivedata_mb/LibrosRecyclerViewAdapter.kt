package com.example.practica_4_livedata_mutablelivedata_mb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class LibrosRecyclerViewAdapter (private var libros : MutableList<Libro>) : RecyclerView.Adapter<LibrosRecyclerViewAdapter.ViewHolder> (){
    var click : ((Int, Libro) -> Unit)? = null
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView
        val autor: TextView

        val button : Button
        init {
            titulo = view.findViewById(R.id.titulo_item)
            autor = view.findViewById(R.id.autor_item)
            button = view.findViewById(R.id.button_elegir_item)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.listado_item, viewGroup, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.titulo.text = libros[position].titulo
        viewHolder.autor.text = libros[position].autor
        viewHolder.button.setOnClickListener(){
            this.click?.let { func -> func(position, libros[position]) }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = libros.size
}