package net.azarquiel.appdeliciasdelatierra.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import net.azarquiel.appdeliciasdelatierra.LoginActivity
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityRegisterBinding
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupViews()
    }

    private fun setupViews() {
        binding.registerButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val nombre = binding.nombre.text.toString()
            val apellidos = binding.apellidos.text.toString()
            val poblacion = binding.poblacion.text.toString()
            val provincia = binding.provincia.text.toString()

            if (validateFields(email, password, nombre, apellidos, poblacion, provincia)) {
                val usuario = Usuario(email = email, password = password, nombre = nombre, apellidos = apellidos, poblacion = poblacion, provincia = provincia)
                registerUser(usuario)
            }
        }

        binding.backToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun validateFields(email: String, password: String, nombre: String, apellidos: String, poblacion: String, provincia: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showCustomToast(this, "Por favor, ingrese un correo electrónico válido.")
            return false
        }
        if (password.length < 6) {
            showCustomToast(this, "Por favor, introduce una contraseña válida, mínimo 6 caracteres.")
            return false
        }
        if (nombre.isEmpty() || apellidos.isEmpty() || poblacion.isEmpty() || provincia.isEmpty()) {
            showCustomToast(this, "Todos los campos son obligatorios.")
            return false
        }
        return true
    }


    private fun registerUser(usuario: Usuario) {
        viewModel.register(usuario).observe(this, Observer { usuarioRegistrado ->
            if (usuarioRegistrado != null) {
                showCustomToast(this,"Registro exitoso")
                navigateToLogin()
            } else {
                showCustomToast(this, "Error al registrar usuario")
            }
        })
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showCustomToast(context: Context, message: String) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.toast_custom, null)

        val imageView = layout.findViewById<ImageView>(R.id.logo)
        imageView.setImageResource(R.drawable.logonasf) // Set your logo

        val textView = layout.findViewById<TextView>(R.id.texto)
        textView.text = message

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}