package net.azarquiel.appdeliciasdelatierra.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterCategorias
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterDelete
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityDeleteBinding
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel

class DeleteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDeleteBinding
    private lateinit var adapter: AdapterDelete
    private lateinit var viewModel: MainViewModel
    private var producto: List<Producto> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Mis Productos"
        initRV()
        val usuario = obtenerUsuario()

        val idusuario: Int = usuario?.idusuario ?: -1

        viewModel.getProductos(idusuario).observe(this, Observer { productos ->

            productos?.let {
                producto = it
                Log.d("Soy tonta22222", "$producto")
                adapter.setProducto(it)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterDelete(this, R.layout.rowdelete)
        binding.deleted.rvdeleteproducto.adapter = adapter
        binding.deleted.rvdeleteproducto.layoutManager = LinearLayoutManager(this)
    }



    private fun obtenerUsuario(): Usuario? {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val usuarioJson = sharedPreferences.getString("usuario", null)

        return if (usuarioJson != null) {
            val gson = Gson()
            gson.fromJson(usuarioJson, Usuario::class.java)
        } else {
            null
        }
    }


}