package net.azarquiel.appdeliciasdelatierra.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterCategorias
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityPostFoodBinding
import net.azarquiel.appdeliciasdelatierra.model.Categoria
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostFoodBinding
    private lateinit var viewModel: MainViewModel
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.imgsubirfoto.setOnClickListener {
            openGallery()
        }
        fetchCategories()

        binding.publishFoodButton.setOnClickListener {
            saveProducto()
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            imageUri?.let { loadImageFromUri(it) }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun loadImageFromUri(uri: Uri) {
        try {
            Glide.with(this)
                .load(uri)
                .circleCrop()
                .into(binding.imgsubirfoto)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun fetchCategories() {
        viewModel.getCategoria().observe(this, Observer { categorias ->
            val adapter = AdapterCategorias(this, categorias)
            binding.spinnerCategories.adapter = adapter
        })
    }

    private fun obtenerIdUsuario(): Int {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        return sharedPreferences.getInt("idusuario", -1)
    }

    private fun saveProducto() {
        val nombre = binding.tvproducto.text.toString()
        val descripcion = binding.tvdescipcion.text.toString()
        val fecha = Date()
        val estado = "Disponible"
        val idusuario = obtenerIdUsuario()
        val idCategoria = (binding.spinnerCategories.selectedItem as Categoria).idcategoria

        val formatoFecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
        val fechaFormateada = formatoFecha.format(fecha)

        if (imageUri != null) {
            val imagen = uriToByteArray(imageUri!!)
            Log.d("IMAGEN", "ByteArray length: ${imagen.size}")

            val producto = Producto(nombre, descripcion, fechaFormateada, estado, idCategoria, imagen, idusuario)

            viewModel.saveProducto(producto).observe(this, Observer { productoGuardado ->
                if (productoGuardado != null) {
                    this.showToast("Producto guardado con éxito")
                } else {
                    this.showToast("Error al guardar el producto")
                }
            })
        } else {
            this.showToast("Por favor, selecciona una imagen para el producto")
        }
    }

    private fun uriToByteArray(uri: Uri): ByteArray {
        val inputStream = contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        inputStream?.use { it.copyTo(byteArrayOutputStream) }
        return byteArrayOutputStream.toByteArray()
    }

    private fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}