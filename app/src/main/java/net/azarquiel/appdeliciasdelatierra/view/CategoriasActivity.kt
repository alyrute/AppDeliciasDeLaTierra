package net.azarquiel.appdeliciasdelatierra.view

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterCategorias
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterProducto
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityCategoriasBinding
import net.azarquiel.appdeliciasdelatierra.databinding.ActivitySearcherFoodBinding
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel

class CategoriasActivity : AppCompatActivity() {

    private lateinit var categoria: List<Categoria>
    private lateinit var binding: ActivityCategoriasBinding
    private lateinit var adapter: AdapterCategorias
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initRV()
        getCategoria()
    }

    private fun getCategoria() {
        viewModel.getCategoria().observe(this, Observer {
            it?.let{
                categoria = it
                adapter.setCategoria(it)
            }
        })
    }
    private fun initRV() {
        adapter = AdapterCategorias(this, R.layout.rowcategoria)
        binding.cat.rvcategoria.adapter = adapter
        binding.cat.rvcategoria.layoutManager = LinearLayoutManager(this)
    }
}