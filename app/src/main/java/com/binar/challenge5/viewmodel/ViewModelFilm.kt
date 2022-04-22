package com.binar.challenge5.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.challenge5.ProfileActivity
import com.binar.challenge5.model.GetAllFilmResponseItem
import com.binar.challenge5.model.Responseuser
import com.binar.challenge5.network.APIClient
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFilm : ViewModel() {

    lateinit var liveDataFilm : MutableLiveData<List<GetAllFilmResponseItem>>

    init {
        liveDataFilm = MutableLiveData()
    }

    fun getLiveFilmObserver() : MutableLiveData<List<GetAllFilmResponseItem>> {
        return liveDataFilm
    }
    fun makeApiFilm(){
        APIClient.instance.getAllFilm()
            .enqueue(object : retrofit2.Callback<List<GetAllFilmResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetAllFilmResponseItem>>,
                    response: Response<List<GetAllFilmResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    }else{
                        liveDataFilm.postValue(null)
                    }

                }

                override fun onFailure(call: Call<List<GetAllFilmResponseItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)


                }

            })
    }



}