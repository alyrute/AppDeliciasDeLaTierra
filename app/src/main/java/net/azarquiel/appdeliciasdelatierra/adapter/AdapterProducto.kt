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
class AdapterProducto(val context: Context,
                      val layout: Int

) : RecyclerView.Adapter<AdapterProducto.ViewHolder>() {

    private var dataList: List<Producto> = emptyList()

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

    internal fun setProducto (productos: List<Producto>) {
        this.dataList = productos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Producto){

            val ivrowproducto = itemView.findViewById(R.id.ivrowproducto) as ImageView
            val tvrownombreproducto = itemView.findViewById(R.id.tvrownombreproducto) as TextView
            val tvrowdescripcion = itemView.findViewById(R.id.tvrowdescripcion) as TextView




            tvrownombreproducto.text = dataItem.nombre
            tvrowdescripcion.text= dataItem.descripcion

            val base64Image = dataItem.imagen
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            ivrowproducto.setImageBitmap(bitmap)


            itemView.tag = dataItem
        }

    }
}