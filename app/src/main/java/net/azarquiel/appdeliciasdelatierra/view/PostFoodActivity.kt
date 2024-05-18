package net.azarquiel.appdeliciasdelatierra.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var viewModel: MainViewModel
    private var imageUri=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val imageView = binding.imgsubirfoto
        imageView.setOnClickListener {
            openGallery()
        }
        fetchCategories()


        val publishFoodButton = binding.publishFoodButton
        publishFoodButton.setOnClickListener {
            saveProducto()
        }


    }
    //Logica Fotos
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri: Uri? = data?.data
            uri?.let { loadImageFromUri(it) }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun loadImageFromUri(uri: Uri) {
        try {
            val imageView = binding.imgsubirfoto
            Glide.with(this)
                .load(uri)
                .circleCrop()
                .into(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Logica para desplegable

    private fun fetchCategories() {
        viewModel.getCategoria().observe(this, Observer { categorias ->
            val spinner: Spinner = findViewById(R.id.spinnerCategories)
            val adapter = AdapterCategorias(this, categorias)
            spinner.adapter = adapter
        })
    }

    private fun setCategories(categories: List<String>) {
        val spinner = binding.spinnerCategories
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun saveProducto() {
        val nombre = binding.tvproducto.text.toString()
        val descripcion = binding.tvdescipcion.text.toString()
        val fecha = Date()
        val estado = "Disponible"
        val idCategoria = (binding.spinnerCategories.selectedItem as Categoria).idcategoria
        val imagen = imageUri?.let { uriToByteArray(it) } ?: ByteArray(0)

        val formatoFecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
        val fechaFormateada = formatoFecha.format(fecha)

        val producto = Producto( nombre, descripcion, fecha, estado, idCategoria, imagen)

        viewModel.saveProducto(producto).observe(this, Observer { productoGuardado ->

            Toast.makeText(this, "Producto guardado con éxito", Toast.LENGTH_SHORT).show()
        })
    }

    private fun uriToByteArray(uri: Uri): ByteArray {
        val inputStream = contentResolver.openInputStream(uri)
        val byteArrayOutputStream = ByteArrayOutputStream()
        inputStream?.use { it.copyTo(byteArrayOutputStream) }
        return byteArrayOutputStream.toByteArray()
    }
}