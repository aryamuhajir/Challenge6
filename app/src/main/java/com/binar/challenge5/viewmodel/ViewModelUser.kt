package com.binar.challenge5.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.challenge5.ProfileActivity
import com.binar.challenge5.model.Responseuser
import com.binar.challenge5.network.APIClient
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel() {
    lateinit var liveDataUser : MutableLiveData<Responseuser>

    init {
        liveDataUser = MutableLiveData()
    }

    fun getLiveUserObserver() : MutableLiveData<Responseuser> {
        return liveDataUser
    }

    fun updateUser(id : String, username : String, nameLengkap : String, alamat : String, tanggal : String){
        APIClient.instance.updateUser(id.toInt(), username, nameLengkap, alamat, tanggal)
            .enqueue(object : Callback<Responseuser> {
                override fun onResponse(
                    call: Call<Responseuser>,
                    response: Response<Responseuser>
                ) {
                    if (response.isSuccessful){
                        liveDataUser.postValue(response.body())

                    }else{
                        liveDataUser.postValue(null)
                    }


                }

                override fun onFailure(call: Call<Responseuser>, t: Throwable) {
                    liveDataUser.postValue(null)

                }

            })

    }



}