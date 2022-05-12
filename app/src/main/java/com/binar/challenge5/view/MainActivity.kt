package com.binar.challenge5.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.binar.challenge5.R
import com.binar.challenge5.model.Responseuser
import com.binar.challenge5.network.APIClient
import com.binar.challenge5.viewmodel.ViewModelUser
import com.binar.challenge5.manager.UserManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response

class MainActivity : AppCompatActivity() {
    //lateinit var prefs2 : SharedPreferences
    lateinit var userManager: UserManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userManager = UserManager(this)
        //prefs2 =  this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)


        txtBlmPunyaAkun.setOnClickListener {
            startActivity(Intent(this, RegisterActivitiy::class.java))
        }

        btnLogin.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            login()
        }
    }
    fun login(email : String, password : String){
        val viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLoginUserObserver().observe(this, Observer {
            if (it != null){
                GlobalScope.launch {
                    userManager.login(it.username, it.password, it.address, it.completeName, it.dateofbirth, it.email, it.id)
                    userManager.setStatus("yes")
                }

//                val sf = prefs2.edit()
//                sf.putString("ID", it?.id)
//                sf.putString("USERNAME", it?.username)
//                sf.putString("EMAIL", it?.email)
//                sf.putString("NAMALENGKAP", it?.completeName)
//                sf.putString("TANGGALLAHIR", it?.dateofbirth)
//                sf.putString("ALAMAT", it?.address)
//                sf.apply()
                Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show()
                finish()

                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            }else{
                Toast.makeText(this, "Terdapat kesalahan input atau Jaringan!", Toast.LENGTH_LONG).show()
            }

        })
        viewModel.loginAPI(email, password)

    }

    fun login(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()
        if (email.isNotBlank() && password.isNotBlank()){
            APIClient.instance.loginUser(email, password)
                .enqueue(object : Callback<Responseuser>{
                    override fun onResponse(call: Call<Responseuser>, response: Response<Responseuser>) {
                        if (response.code().toString().equals("404")){
                            Toast.makeText(applicationContext, "Email atau password salah!", Toast.LENGTH_LONG).show()
                        }else{
                            GlobalScope.launch {
                                userManager.login(response.body()!!.username, response.body()!!.password, response.body()!!.address, response.body()!!.completeName, response.body()!!.dateofbirth, response.body()!!.email, response.body()!!.id)
                                userManager.setStatus("yes")
                            }
//                            val sf = prefs2.edit()
//                            sf.putString("ID", response.body()?.id)
//                            sf.putString("USERNAME", response.body()?.username)
//                            sf.putString("EMAIL", response.body()?.email)
//                            sf.putString("NAMALENGKAP", response.body()?.completeName)
//                            sf.putString("TANGGALLAHIR", response.body()?.dateofbirth)
//                            sf.putString("ALAMAT", response.body()?.address)
//                            sf.apply()
                            //Toast.makeText(applicationContext, response.body()?.toString(), Toast.LENGTH_LONG).show()
                            finish()

                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        }
                    }

                    override fun onFailure(call: Call<Responseuser>, t: Throwable) {
                        Toast.makeText(applicationContext, "Gangguan API atau Jaringan!", Toast.LENGTH_LONG).show()

                    }

                })
        }else{
            Toast.makeText(applicationContext, "Email dan Password harus diisi!", Toast.LENGTH_LONG).show()

        }


    }
}