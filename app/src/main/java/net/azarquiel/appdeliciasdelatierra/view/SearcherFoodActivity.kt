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
import com.google.gson.Gson
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
    private var idusuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearcherFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoria = intent.getSerializableExtra("categoria") as Categoria
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val usuario = obtenerUsuario()

        val idusuario: Int = usuario?.idusuario ?: -1

        initRV()
        searchProducto()


        viewModel.getProductosPorCategoria(categoria.idcategoria).observe(this, Observer { productos ->
            productos?.let {
                producto = it
                // Filtra los productos cuyo idusuario sea diferente al del usuario logeado
                val productosFiltrados = it.filter { producto -> producto.usuario.idusuario != idusuario }
                adapter.setProducto(productosFiltrados)
            }
        })
    }



    private fun searchProducto() {
        binding.searchField.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            onQueryTextChange(text.toString())
        })
    }

    fun EditText.addTextChangedListener(
        beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
        onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
        afterTextChanged: ((Editable?) -> Unit)? = null
    ) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeTextChanged?.invoke(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged?.invoke(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) {
                afterTextChanged?.invoke(s)
            }
        }
        addTextChangedListener(textWatcher)
    }

    private fun initRV() {
        adapter = AdapterProducto(this, R.layout.rowproducto)
        binding.search.rvproducto.adapter = adapter
        binding.search.rvproducto.layoutManager = LinearLayoutManager(this)
    }

    private fun onQueryTextChange(query: String) {
        if (query.isEmpty()) {
            // Muestra todos los productos cuando la consulta de búsqueda está vacía
            adapter.setProducto(producto)
        } else {
            // Filtra los productos basándose en la consulta de búsqueda y que no sean del usuario logeado
            val filteredProductos = producto.filter { producto ->
                producto.nombre.contains(query, ignoreCase = true) && producto.usuario.idusuario != idusuario
            }
            adapter.setProducto(filteredProductos)
        }
    }

    fun onClickProducto(v: View) {
        val productoPulsado = v.tag as Producto
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra("producto", productoPulsado)
        startActivity(intent)
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