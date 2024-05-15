package net.azarquiel.appdeliciasdelatierra.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import net.azarquiel.appdeliciasdelatierra.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = View(this)
        view.background = ColorDrawable(Color.parseColor("#80000000"))
        setContentView(view)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)

    }
}