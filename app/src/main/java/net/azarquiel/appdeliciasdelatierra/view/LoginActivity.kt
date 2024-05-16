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


        binding.loginButton.setOnClickListener {
            val emailEditText = binding.email
            val email = emailEditText.text.toString()
            val passwordEditText = binding.password
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.getLogin(email, password)
                viewModel.usuario.observe(this, Observer { usuario ->
                    if (usuario != null) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Finalizar LoginActivity para que no se pueda regresar presionando el botón de atrás
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Por favor ingresa email y contraseña", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val botonRegistro = binding.register
        botonRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
