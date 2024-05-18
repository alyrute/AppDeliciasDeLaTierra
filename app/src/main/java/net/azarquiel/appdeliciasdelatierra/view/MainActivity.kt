package net.azarquiel.appdeliciasdelatierra.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import net.azarquiel.appdeliciasdelatierra.R
import net.azarquiel.appdeliciasdelatierra.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.myToolbar
        setSupportActionBar(toolbar)

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Usuario")

        // Establecer el título de la barra de acción
        supportActionBar?.title = "Bienvenid@ $username"

        val searchFoodButton = binding.searchFoodButton
        searchFoodButton.setOnClickListener {
            val intent = Intent(this, SearcherFoodActivity::class.java)
            startActivity(intent)
        }

        val publishFoodButton = binding.publishFoodButton
        publishFoodButton.setOnClickListener {
            val intent = Intent(this, PostFoodActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_button -> {
                // Aquí va tu código para manejar el clic en el botón de logout
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }







}