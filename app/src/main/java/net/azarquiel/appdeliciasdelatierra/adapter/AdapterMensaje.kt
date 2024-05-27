package net.azarquiel.appdeliciasdelatierra.adapter

import android.content.Context
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
class AdapterMensaje(val context: Context,
                     val layout: Int
) : RecyclerView.Adapter<AdapterMensaje.ViewHolder>() {

    private var dataList: List<Mensaje> = emptyList()
    private var mensajes: MutableList<Mensaje> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setMensaje (mensaje: List<Mensaje>) {
        this.dataList = mensaje
        notifyDataSetChanged()
    }

    fun addMensaje(mensaje: Mensaje) {
        mensajes.add(mensaje)
        notifyItemInserted(mensajes.size - 1)
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Mensaje){


            val textmensaje = itemView.findViewById(R.id.textmensaje) as TextView




            textmensaje.text = dataItem.texto




            itemView.tag = dataItem
        }

    }
}