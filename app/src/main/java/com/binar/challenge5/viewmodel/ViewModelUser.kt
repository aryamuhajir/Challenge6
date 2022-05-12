package com.binar.challenge5.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.challenge5.model.ResponseRegister
import com.binar.challenge5.model.Responseuser
import com.binar.challenge5.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel() {
    lateinit var liveDataUser : MutableLiveData<Responseuser>
    lateinit var loginUser : MutableLiveData<Responseuser>
    lateinit var registerUser : MutableLiveData<ResponseRegister>
    lateinit var pesan : MutableLiveData<String>
    lateinit var sf : SharedPreferences





    init {
        liveDataUser = MutableLiveData()
        loginUser = MutableLiveData()
        registerUser = MutableLiveData()
        pesan = MutableLiveData()
    }

    fun getLiveUserObserver() : MutableLiveData<Responseuser> {
        return liveDataUser
    }
    fun getLoginUserObserver() : MutableLiveData<Responseuser> {
        return loginUser
    }
    fun getRegisterUserObserver() : MutableLiveData<ResponseRegister> {
        return registerUser
    }
    fun getPesanObserver() : MutableLiveData<String>{
        return pesan
    }
    fun ambilPesan(pesan : String) : String {
        return  pesan
    }

    fun loginAPI(email : String, password : String){
        if (email.isNotBlank() && password.isNotBlank()){
            APIClient.instance.loginUser(email, password)
                .enqueue(object : Callback<Responseuser>{
                    override fun onResponse(call: Call<Responseuser>, response: Response<Responseuser>) {
                        if (response.code().toString().equals("404")){
                            ambilPesan("Email atau password salah!")
                            liveDataUser.postValue(null)
                        }else{
                            //Toast.makeText(applicationContext, response.body()?.toString(), Toast.LENGTH_LONG).show()
                            liveDataUser.postValue(response.body())
                        }
                    }
                    override fun onFailure(call: Call<Responseuser>, t: Throwable) {
                        ambilPesan("Gangguan API atau Jaringan!")
                        liveDataUser.postValue(null)

                    }

                })
        }else{
            ambilPesan("Email dan Password harus diisi!")
            liveDataUser.postValue(null)


        }


    }

    fun updateUser(id : Int, username : String, nameLengkap : String, alamat : String, tanggal : String){
        APIClient.instance.updateUser(id, username, nameLengkap, alamat, tanggal)
            .enqueue(object : Callback<Responseuser> {
                override fun onResponse(
                    call: Call<Responseuser>,
                    response: Response<Responseuser>
                ) {
                    liveDataUser.postValue(response.body())

                }
                override fun onFailure(call: Call<Responseuser>, t: Throwable,  ) {
                    liveDataUser.postValue(Responseuser(alamat, nameLengkap, tanggal, "", "", "",username))

                }

            })

    }



}