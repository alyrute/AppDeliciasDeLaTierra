package net.azarquiel.appdeliciasdelatierra

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityLoginBinding
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityMainBinding
import net.azarquiel.appdeliciasdelatierra.model.Usuario
import net.azarquiel.appdeliciasdelatierra.view.MainActivity
import net.azarquiel.appdeliciasdelatierra.view.RegisterActivity
import net.azarquiel.appdeliciasdelatierra.viewmodel.MainViewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupViews()
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
                navigateToMain()
            } else {
                showToast("Credenciales incorrectas")
            }
        })
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