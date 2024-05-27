package net.azarquiel.appdeliciasdelatierra.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.adapter.AdapterMensaje
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityContactBinding
import net.azarquiel.appdeliciasdelatierra.model.Mensaje
import net.azarquiel.appdeliciasdelatierra.model.Producto
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel


class ContactActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityContactBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var producto: Producto
    private lateinit var mensaje: List<Mensaje>
    private lateinit var adapter: AdapterMensaje
    private lateinit var usuario: Usuario




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        producto = intent.getSerializableExtra("producto") as Producto


        initRV()
        obtenerIdUsuario()
        setupObservers()

        viewModel.getMensajesByUsuario(producto.idproducto!!).observe(this, Observer { mensajes ->
            // Actualizar el adaptador con la nueva lista de mensajes
            adapter.setMensaje(mensajes)
        })

        viewModel.getUsuarioByProducto(producto.idproducto!!).observe(this, Observer { usuario ->
            Log.d("ContactActivity", "Usuario: $usuario")
            if (usuario != null) {
                binding.contacNombe.text = usuario.nombre
                binding.contacPobPro.text = "${usuario.poblacion}, ${usuario.provincia}"
            }
        })
        binding.bntenviar.setOnClickListener {
            //enviarMensaje()
        }

    }


    private fun initRV() {
        adapter = AdapterMensaje(this, R.layout.rowcontact)
        binding.contact.rvmensaje.layoutManager = LinearLayoutManager(this)
        binding.contact.rvmensaje.adapter = adapter
    }

    private fun enviarMensajeAlReceptor(mensaje: Mensaje) {
        viewModel.insertarMensaje(mensaje)

        binding.tvMensaje.text.clear()
    }


   /* private fun enviarMensaje() {
        val textoMensaje = binding.tvMensaje.text.toString()
        if (textoMensaje.isNotEmpty()) {
            val formatoFecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
            val fechaFormateada = formatoFecha.format(Date())
            val idUsuario = obtenerIdUsuario()


            val mensaje = Mensaje(
                receiverid = usuario
                senderid = usuario
                texto = textoMensaje,
                fecha = fechaFormateada,
                leido = false )
            viewModel.mensajeLiveData.observe(this, Observer { mensajeEnviado ->
                if (mensajeEnviado != null) {
                    // Limpiar el campo de texto
                    binding.tvMensaje.text.clear()
                    // Agregar el mensaje al adaptador y actualizar la vista
                    adapter.addMensaje(mensajeEnviado)
                    adapter.notifyDataSetChanged()
                } else {
                    // Mostrar algún mensaje de error o realizar alguna acción en caso de fallo
                }
            })
        }
    }*/

    private fun obtenerIdUsuario(): Int {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val idUsuario = sharedPreferences.getInt("idusuario", -1)
        Log.d("LoginActivity", "ID Usuario: $idUsuario")
        return idUsuario
    }

    private fun setupObservers() {
        viewModel.mensajeLiveData.observe(this, Observer { mensajeEnviado: Mensaje? ->
            if (mensajeEnviado != null) {
                binding.tvMensaje.text.clear()
                adapter.addMensaje(mensajeEnviado)
                adapter.notifyDataSetChanged()
            } else {
                Log.d("ContactActivity", "El mensaje no fue enviado")
            }
        })
    }




}