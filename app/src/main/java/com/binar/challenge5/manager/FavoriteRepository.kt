package com.binar.challenge5.manager

import androidx.lifecycle.LiveData
import com.binar.challenge5.model.Favorite

class FavoriteRepository(private val favDao : FavoriteDao) {

    val allFav : LiveData<List<Favorite>> = favDao.getFav()

    suspend fun insert(favorite: Favorite){
        favDao.insertFavorite(favorite)
    }
    suspend fun delete(id: String){
        favDao.deleteFavoriteFilmById(id)
    }
    suspend fun cek(id : String) : Int {
        return favDao.cekFilm(id)
    }
}