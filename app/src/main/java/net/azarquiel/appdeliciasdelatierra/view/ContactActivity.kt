package net.azarquiel.appdeliciasdelatierra.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.launch
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterMensaje
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityContactBinding
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var producto: Producto
    private lateinit var adapter: AdapterMensaje

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        producto = intent.getSerializableExtra("producto") as Producto



        val usuario = obtenerUsuario()
        Log.d("UsuarioContac", "$usuario")

       // initRV()
        setupObservers()

        val idproducto: Int = producto.idproducto?:-1
        val senderid: Int = usuario?.idusuario ?: -1
       // val receiverid: Int = producto.idusuario ?: -1

        // Obtener mensajes
      /*  viewModel.getMensajesByIdProducto(idproducto, senderid, receiverid).observe(this, Observer { mensajes ->
            adapter.setMensajes(mensajes)
            adapter.notifyDataSetChanged()
        })

        // Obtener detalles del usuario por su ID y registrar la información en los logs
        viewModel.getUsuarioById(producto.idusuario ?: -1).observe(this, Observer { usuario ->
            if (usuario != null) {
                binding.contacNombe.text = usuario.nombre
                binding.contacPobPro.text = "${usuario.poblacion}, ${usuario.provincia}"
                Log.d("ContactActivity", "Usuario: ${usuario.nombre}, Población: ${usuario.poblacion}, Provincia: ${usuario.provincia}")
            } else {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        })

        binding.bntenviar.setOnClickListener {
            //enviarMensaje()
        }
    }*/
/*
    private fun initRV() {
        adapter = AdapterMensaje(this, R.layout.rowcontact)
        binding.contact.rvmensaje.layoutManager = LinearLayoutManager(this)
        binding.contact.rvmensaje.adapter = adapter*/
    }
/*
    private fun enviarMensaje() {
        val textoMensaje = binding.tvMensaje.text.toString()
        if (textoMensaje.isNotEmpty()) {
            val formatoFecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
            val fechaFormateada = formatoFecha.format(Date())
            val usuario = obtenerUsuario()

            val mensaje = producto.idproducto?.let {
                Mensaje(
                    receiverid = producto.idproducto,
                    senderid = usuario?.idusuario?:-1,
                    texto = textoMensaje,
                    fecha = fechaFormateada,
                    leido = false,
                    idproducto = it
                )
            }
            mensaje?.let {
                lifecycleScope.launch {
                    enviarMensajeAlReceptor(it)
                }
            }
        }
    }*/

    private fun enviarMensajeAlReceptor(mensaje: Mensaje) {
        lifecycleScope.launch {
            viewModel.insertarMensaje(mensaje)
            binding.tvMensaje.text.clear()
        }
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

    private fun setupObservers() {
        viewModel.mensajeLiveData.observe(this, Observer { mensajeEnviado ->
            if (mensajeEnviado != null) {
                adapter.addMensaje(mensajeEnviado)
                binding.tvMensaje.text.clear()
            } else {
                Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
