package com.binar.challenge5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.binar.challenge5.model.DataItem
import com.binar.challenge5.model.Responseuser
import com.binar.challenge5.model.User
import com.binar.challenge5.network.APIClient
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var prefs2 : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs2 =  this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)


        txtBlmPunyaAkun.setOnClickListener {
            startActivity(Intent(this, RegisterActivitiy::class.java))
        }

        btnLogin.setOnClickListener {
            login()
        }
    }

    fun login(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        if (email.isNotBlank() && password.isNotBlank()){
            APIClient.instance.loginUser(email, password)
                .enqueue(object : Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.code().toString().equals("404")){
                            Toast.makeText(applicationContext, "Email atau password salah!", Toast.LENGTH_LONG).show()
                        }else{
                            val sf = prefs2.edit()
                            sf.putString("ID", response.body()?.responseuser?.id)
                            sf.putString("USERNAME", response.body()?.responseuser?.username)
                            sf.putString("EMAIL", response.body()?.responseuser?.email)
                            sf.putString("NAMALENGKAP", response.body()?.responseuser?.completeName)
                            sf.putString("TANGGALLAHIR", response.body()?.responseuser?.dateofbirth)
                            sf.putString("ALAMAT", response.body()?.responseuser?.address)
                            sf.apply()

                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(applicationContext, "Gangguan API atau Jaringan!", Toast.LENGTH_LONG).show()

                    }

                })
        }else{
            Toast.makeText(applicationContext, "Email dan Password harus diisi!", Toast.LENGTH_LONG).show()

        }


    }
}