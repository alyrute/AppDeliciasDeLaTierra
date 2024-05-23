package net.azarquiel.appdeliciasdelatierra.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterProducto
import net.azarquiel.appdeliciasdelatierra.databinding.ActivitySearcherFoodBinding
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel


class SearcherFoodActivity : AppCompatActivity() {
    private lateinit var producto: List<Producto>
    private lateinit var binding: ActivitySearcherFoodBinding
    private lateinit var adapter: AdapterProducto
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearcherFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRV()
        getProductos()




    }

    private fun getProductos() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getProductos().observe(this, Observer {
            it?.let{
                producto = it
                adapter.setProducto(it)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterProducto(this, R.layout.row_producto)
        binding.search.rvproducto.adapter = adapter
        binding.search.rvproducto.layoutManager = LinearLayoutManager(this)
    }


}