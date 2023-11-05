package com.example.aplicacionprueba

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class Adapter(var context:Context, private val lista: ArrayList<ObjetoLista>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(val vista: View): RecyclerView.ViewHolder(vista) {
        var foto:ImageView? = null
        var texto:TextView? = null
        var description:TextView? = null

        init {
            foto = vista.findViewById(R.id.imageElement)
            texto = vista.findViewById(R.id.titulo)
            description = vista.findViewById(R.id.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_layout, parent, false)
        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val producto = lista[position]
        holder.texto?.text = producto.texto
        holder.description?.text = producto.description
        Glide.with(context)
            .load(producto.imagenURL)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.foto!!)
    }
}