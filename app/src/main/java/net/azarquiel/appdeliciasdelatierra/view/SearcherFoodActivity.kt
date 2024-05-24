package net.azarquiel.appdeliciasdelatierra.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterProducto
import net.azarquiel.appdeliciasdelatierra.databinding.ActivitySearcherFoodBinding
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel

class SearcherFoodActivity : AppCompatActivity() {
    private lateinit var producto: List<Producto>
    private lateinit var binding: ActivitySearcherFoodBinding
    private lateinit var adapter: AdapterProducto
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearcherFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRV()
        getProductos()
        loadUserDetails()

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

    private fun getProductos() {
        viewModel.getProductos().observe(this, Observer {
            it?.let{
                producto = it
                adapter.setProducto(it)
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
    private fun loadUserDetails() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nombre = sharedPreferences.getString("nombre", null)
        val apellidos = sharedPreferences.getString("apellidos", null)
        val poblacion = sharedPreferences.getString("poblacion", null)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        val provincia = sharedPreferences.getString("provincia", null)

        if (nombre != null && apellidos != null && poblacion != null && email != null && password != null && provincia != null) {
            usuario = Usuario(idusuario = null, nombre = nombre, apellidos = apellidos, email = email, password = password, poblacion = poblacion, provincia = provincia)
        }
    }
}
