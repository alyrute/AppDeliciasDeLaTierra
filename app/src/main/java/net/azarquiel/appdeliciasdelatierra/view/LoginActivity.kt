package net.azarquiel.appdeliciasdelatierra

import android.content.Context
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
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        setupViews()
    }

    private fun setupViews() {
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                showCustomToast(this, "Por favor, ingrese el email y contraseña.")
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
                saveUserName(usuario.nombre)
                saveUsuario(usuario)
                navigateToMain(usuario.nombre)
            } else {
                showCustomToast(this, "Fallo el login, vuelva a intentarlo")
            }
        })
    }

    private fun saveUserName(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    private fun saveUsuario(usuario: Usuario) {
        val gson = Gson()
        val usuarioJson = gson.toJson(usuario)
        val editor = sharedPreferences.edit()
        editor.putString("usuario", usuarioJson)
        editor.apply()
    }

    private fun navigateToMain(username: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
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
