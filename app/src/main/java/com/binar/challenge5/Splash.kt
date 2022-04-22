package com.binar.challenge5

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            if (this.getSharedPreferences("datalogin", Context.MODE_PRIVATE).contains("ID")){
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                startActivity(Intent(this, MainActivity::class.java))

            }
            MainActivity().finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}