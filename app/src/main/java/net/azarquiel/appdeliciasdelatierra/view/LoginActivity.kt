package net.azarquiel.appdeliciasdelatierra

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityLoginBinding
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.view.MainActivity
import net.azarquiel.appdeliciasdelatierra.view.RegisterActivity
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null
    private lateinit var sh: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        checkLogin()
        setupViews()
        obtenerUsuario()


    }

    private fun checkLogin() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val idusuario = sharedPreferences.getInt("idusuario", -1)

        if (username != null && idusuario != -1) {
            navigateToMain()
        }
    }

    private fun setupViews() {
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                showToast("Por favor ingresa email y contraseña")
            }
        }

        binding.register.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun loginUser(email: String, password: String) {
        viewModel.getLogin(email, password)
        viewModel.usuario.observe(this, Observer { usuario ->
            if (usuario != null) {
                usuario.idusuario?.let { saveUserDetails(usuario.nombre, it) }
                navigateToMain()
            } else {
                showToast("Credenciales incorrectas")
            }
        })
    }

    private fun saveUserDetails(nombre: String, idusuario: Int) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", nombre)
        editor.putInt("idusuario", idusuario)
        editor.apply()
        Log.d("LoginActivity", "Detalles guardados - Nombre: $nombre, IDUsuario: $idusuario")
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

    private fun guardarUsuario(usuario: Usuario) {
        val gson = Gson()
        val usuarioJson = gson.toJson(usuario)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("usuario", usuarioJson)
        editor.apply()
    }


    private fun checkIfLoggedIn() {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val idusuario = sharedPreferences.getInt("idusuario", -1)

        if (username != null && idusuario != -1) {
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }



}