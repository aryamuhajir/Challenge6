package com.binar.challenge5.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.lifecycle.asLiveData
import com.binar.challenge5.R
import com.binar.challenge5.manager.UserManager

class Splash : AppCompatActivity() {
    lateinit var userManager : UserManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        userManager = UserManager(this)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            userManager.userSTATUS.asLiveData().observe(this) {
                if (it.equals("yes")){
                    startActivity(Intent(this, HomeActivity::class.java))
                }else{
                    startActivity(Intent(this, MainActivity::class.java))

                }
            }
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}