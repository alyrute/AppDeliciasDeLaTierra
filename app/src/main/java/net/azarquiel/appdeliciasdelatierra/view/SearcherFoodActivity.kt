package net.azarquiel.appdeliciasdelatierra.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterProducto
import net.azarquiel.appdeliciasdelatierra.databinding.ActivitySearcherFoodBinding
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel
import java.io.Serializable

class SearcherFoodActivity : AppCompatActivity() {
    private lateinit var producto: List<Producto>
    private lateinit var binding: ActivitySearcherFoodBinding
    private lateinit var adapter: AdapterProducto
    private lateinit var viewModel: MainViewModel
    private lateinit var categoria: Categoria
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearcherFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoria = intent.getSerializableExtra("categoria") as Categoria
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRV()
        searchProducto()
        checkLogin()


    }
    private fun checkLogin() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val idusuario = sharedPreferences.getInt("idusuario", -1)

        if (idusuario != -1) {
            getProductos(idusuario)
        }
    }

    private fun searchProducto() {
        val searchField = findViewById<EditText>(R.id.search_field)
        searchField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                onQueryTextChange(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun getProductos(idusuario: Int) {
        viewModel.getProductosPorCategoria(categoria.idcategoria).observe(this, Observer { productos ->
            productos?.let {
                // Filtra los productos para excluir los del usuario conectado
                val productosFiltrados = it.filter { producto -> producto.idusuario != idusuario }
                producto = productosFiltrados
                adapter.setProducto(productosFiltrados)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterProducto(this, R.layout.rowproducto)
        binding.search.rvproducto.adapter = adapter
        binding.search.rvproducto.layoutManager = LinearLayoutManager(this)
    }

    fun onQueryTextChange(query: String): Boolean {
        val original = ArrayList<Producto>(producto)
        adapter.setProducto(original.filter { producto -> producto.nombre.contains(query,true) })
        return false
    }

    fun onClickProducto(v: View){
        val productoPulsado = v.tag as Producto
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("producto", productoPulsado)
        startActivity(intent)
    }

}
