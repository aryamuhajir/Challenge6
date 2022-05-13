package com.binar.challenge5.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.binar.challenge5.model.Favorite

@Dao
interface FavoriteDao {
    @Insert
    fun insertFavorite(favorite : Favorite) : Long

    @Query("SELECT * FROM Favorite")
    fun getFav(): LiveData<List<Favorite>>
    @Query("SELECT EXISTS(SELECT * FROM Favorite WHERE id = :id)")
    fun cekFilm(id: String) :Int


    @Query("DELETE FROM Favorite WHERE id = :id")
    suspend fun deleteFavoriteFilmById(id: String)


}