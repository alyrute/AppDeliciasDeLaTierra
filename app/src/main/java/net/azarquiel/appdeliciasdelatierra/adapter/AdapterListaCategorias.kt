package net.azarquiel.appdeliciasdelatierra.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import net.azarquiel.appdeliciasdelatierra.model.Categoria


/**
 * Created by pacopulido on 9/10/18.
 */
class AdapterListaCategorias(context: Context, categorias: List<Categoria>) : ArrayAdapter<Categoria>(context, android.R.layout.simple_spinner_item, categorias) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_spinner_item, null)
        }

        val item = getItem(position)
        val tv = view?.findViewById<TextView>(android.R.id.text1)
        tv?.text = item?.nombre

        return view!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null)
        }

        val item = getItem(position)
        val tv = view?.findViewById<TextView>(android.R.id.text1)
        tv?.text = item?.nombre

        return view!!
    }
}