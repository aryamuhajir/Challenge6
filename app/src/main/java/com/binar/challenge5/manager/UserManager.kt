package com.binar.challenge5.manager

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context : Context) {
    // dua data store, data user dan data status user
    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs2")
    private val dataStore2 : DataStore<Preferences> = context.createDataStore(name = "status")
    private val imageData : DataStore<Preferences> = context.createDataStore(name = "image")


    companion object {
        //object data
        val USERNAME = preferencesKey<String>("USERNAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val ADDRESS = preferencesKey<String>("ADDRESS")
        val COMPLETE = preferencesKey<String>("COMPLETE")
        val DATEOFBIRTH = preferencesKey<String>("DATEOFBIRTH")
        val EMAIL = preferencesKey<String>("EMAIL")
        val ID = preferencesKey<String>("ID")
        val STATUS = preferencesKey<String>("STATUS")
        val IMAGE = preferencesKey<String>("IMAGE")


    }
    //fungsi login insert data ke datastore
    suspend fun login( username : String, password : String, address : String, complete : String, dateof : String, email : String, id : String){
        dataStore.edit {
            it[USERNAME] = username
            it[PASSWORD] = password
            it[ADDRESS] = address
            it[COMPLETE] = complete
            it[DATEOFBIRTH] = dateof
            it[EMAIL] = email
            it[ID] = id
        }
    }
    //update data yang ada di datastore
    suspend fun update (username : String, address: String, complete: String, dateof: String){
        dataStore.edit {
            it[USERNAME] = username
            it[ADDRESS] = address
            it[COMPLETE] = complete
            it[DATEOFBIRTH] = dateof

        }
    }
    suspend fun setImage(image: String){
        imageData.edit {
            it[IMAGE] = image

        }
    }
    // menghapus data yang ada di datastore
    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }
    //status user di datastore2 (sebagai pengecekan auth)
    suspend fun setStatus(status : String){
        dataStore2.edit {
            it[STATUS] = status
        }
    }
    // sebagai pengakses data yang ada di datastore via livedatya
    val userNAME : Flow<String> = dataStore.data.map {
        it[UserManager.USERNAME] ?: ""
    }
    val userEMAIL : Flow<String> = dataStore.data.map {
        it[UserManager.EMAIL] ?: ""
    }
    val userALAMAT : Flow<String> = dataStore.data.map {
        it[UserManager.ADDRESS] ?: ""
    }
    val userCOMPLETE : Flow<String> = dataStore.data.map {
        it[UserManager.COMPLETE] ?: ""
    }
    val userTANGGAL : Flow<String> = dataStore.data.map {
        it[UserManager.DATEOFBIRTH] ?: ""
    }
    val userID : Flow<String> = dataStore.data.map {
        it[UserManager.ID] ?: ""
    }
    val userPASS : Flow<String> = dataStore.data.map {
        it[UserManager.PASSWORD] ?: ""
    }
    val userSTATUS : Flow<String> = dataStore2.data.map {
        it[UserManager.STATUS] ?: "no"
    }
    val userIMAGE : Flow<String> = imageData.data.map {
        it[UserManager.IMAGE] ?: "x"
    }
}