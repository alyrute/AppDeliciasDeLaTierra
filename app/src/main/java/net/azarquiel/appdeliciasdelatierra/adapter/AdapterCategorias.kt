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
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Producto
class AdapterCategorias(val context: Context,
                        val layout: Int) : RecyclerView.Adapter<AdapterCategorias.ViewHolder>() {

    private var dataList: List<Categoria> = emptyList()
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

    internal fun setCategoria (categorias: List<Categoria>) {
        this.dataList = categorias
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Categoria){

            val ivrowcategoria = itemView.findViewById(R.id.ivrowcategoria) as ImageView
            val tvrownombrecategoria = itemView.findViewById(R.id.tvrownombrecategoria) as TextView


            tvrownombrecategoria.text = dataItem.nombre


            val base64Image = dataItem.imagen
            val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            ivrowcategoria.setImageBitmap(bitmap)




            itemView.tag = dataItem
        }

    }
}