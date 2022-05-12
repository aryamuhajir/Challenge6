package com.binar.challenge5.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.binar.challenge5.manager.FavoriteDatabase
import com.binar.challenge5.manager.FavoriteRepository
import com.binar.challenge5.model.DataFilmBaruItem
import com.binar.challenge5.model.Favorite
import com.binar.challenge5.model.GetAllFilmResponseItem
import com.binar.challenge5.network.APIClient
import com.binar.challenge5.network.APIClient2
import com.binar.challenge5.view.FavoriteActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFilm (application: Application) : AndroidViewModel(application) {
    lateinit var allFav : LiveData<List<Favorite>>
    lateinit var repository: FavoriteRepository

    lateinit var liveDataFilm : MutableLiveData<List<DataFilmBaruItem>>
    lateinit var liveDataFavorite : MutableLiveData<List<Favorite>>
    var dbFav : FavoriteDatabase? = null

    init {
        val dao = FavoriteDatabase.getInstance(application)?.favoriteDao()
        repository = FavoriteRepository(dao!!)
        allFav = repository.allFav
        liveDataFilm = MutableLiveData()
        liveDataFavorite = MutableLiveData()
    }

    fun getLiveFilmObserver() : MutableLiveData<List<DataFilmBaruItem>> {
        return liveDataFilm
    }
    fun insertFav(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(favorite)
    }
    fun deleteFav(id: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(id)
    }

    fun checkFav(id: String) = viewModelScope.launch ( Dispatchers.IO )  {
        repository.cek(id)
    }


//    fun getFavItem(){
//        dbFav = FavoriteDatabase.getInstance(FavoriteActivity())
//        val fav = dbFav?.favoriteDao()?.getFav()
//        if (fav.isNullOrEmpty()){
//            liveDataFavorite.postValue(null)
//
//        }else{
//            liveDataFavorite.postValue(fav!!)
//        }
//
//
//    }

    fun makeApiFilm(){
        APIClient2.instance.getAllFilm2()
            .enqueue(object : Callback<List<DataFilmBaruItem>>{
                override fun onResponse(
                    call: Call<List<DataFilmBaruItem>>,
                    response: Response<List<DataFilmBaruItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    }else{
                        liveDataFilm.postValue(null)
                    }

                }

                override fun onFailure(call: Call<List<DataFilmBaruItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }
//    fun makeApiFilm(){
//        APIClient.instance.getAllFilm()
//            .enqueue(object : retrofit2.Callback<List<GetAllFilmResponseItem>>{
//                override fun onResponse(
//                    call: Call<List<GetAllFilmResponseItem>>,
//                    response: Response<List<GetAllFilmResponseItem>>
//                ) {
//                    if (response.isSuccessful){
//                        liveDataFilm.postValue(response.body())
//                    }else{
//                        liveDataFilm.postValue(null)
//                    }
//
//                }
//
//                override fun onFailure(call: Call<List<GetAllFilmResponseItem>>, t: Throwable) {
//                    liveDataFilm.postValue(null)
//
//
//                }
//
//            })
//    }



}