package com.binar.challenge5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.binar.challenge5.model.ResponseRegister
import com.binar.challenge5.network.APIClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register_activitiy.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivitiy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activitiy)
        btnDaftar.setOnClickListener {
            daftar()
        }

    }

    fun daftar(){
        val username = editUsername1.text.toString()
        val password = editPassword1.text.toString()
        val password1 = editPassword2.text.toString()
        val email = editEmail1.text.toString()
        if (password.equals(password1)){
            APIClient.instance.registerUser(email, password, username)
                .enqueue(object : Callback<ResponseRegister> {
                    override fun onResponse(
                        call: Call<ResponseRegister>,
                        response: Response<ResponseRegister>
                    ) {
                        Toast.makeText(applicationContext, "Berhasil mendaftar", Toast.LENGTH_LONG).show()
                        finish()



                    }

                    override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                        Toast.makeText(applicationContext, "Terdapat gangguan pada API", Toast.LENGTH_LONG).show()

                    }

                })
        }else{
            Toast.makeText(applicationContext, "Konfirmasi password salah!", Toast.LENGTH_LONG).show()

        }

    }


}