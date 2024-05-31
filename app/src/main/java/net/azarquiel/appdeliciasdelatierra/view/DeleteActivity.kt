package net.azarquiel.appdeliciasdelatierra.view

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
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
                adapter.setProducto(it)
            }
        })


    }

    private fun initRV() {
        adapter = AdapterDelete(this, R.layout.rowdelete)
        binding.delete.rvdelete.adapter=adapter
        binding.delete.rvdelete.layoutManager=LinearLayoutManager(this)



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

    fun showConfirmationDialog(productId: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar producto")
            .setMessage("¿Estás seguro de que quieres eliminar este producto?")
            .setPositiveButton("Sí") { dialog, _ ->
                viewModel.deleteProduct(productId).observe(this, Observer { success ->
                    if (success) {
                        // Aquí puedes actualizar tu RecyclerView para reflejar la eliminación del producto
                        adapter.setProducto(producto.filter { it.idproducto != productId })
                    }
                })
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


}