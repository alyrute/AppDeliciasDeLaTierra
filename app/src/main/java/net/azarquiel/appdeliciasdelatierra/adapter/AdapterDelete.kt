package net.azarquiel.appdeliciasdelatierra.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.model.Producto
class AdapterDelete(val context: Context, val layout: Int) : RecyclerView.Adapter<AdapterDelete.ViewHolder>() {

    private var dataList: List<Producto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewlayout = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(viewlayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setProducto(productos: List<Producto>) {
        this.dataList = productos
        notifyDataSetChanged()
    }

    fun getProductos(): List<Producto> {
        return dataList
    }

    inner class ViewHolder(viewlayout: View) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Producto) {
            val ivrowdeleteproducto = itemView.findViewById<ImageView>(R.id.ivrowdeleteproducto)
            val tvrowdeleted = itemView.findViewById<TextView>(R.id.tvrowdeleted)

            tvrowdeleted.text = dataItem.nombre


            val base64Image = dataItem.imagen
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            ivrowdeleteproducto.setImageBitmap(bitmap)

            itemView.tag = dataItem
        }
    }
}