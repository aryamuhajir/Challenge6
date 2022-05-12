package com.binar.challenge5.manager

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context : Context) {
    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user_prefs2")
    private val dataStore2 : DataStore<Preferences> = context.createDataStore(name = "status")

    companion object {
        val USERNAME = preferencesKey<String>("USERNAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val ADDRESS = preferencesKey<String>("ADDRESS")
        val COMPLETE = preferencesKey<String>("COMPLETE")
        val DATEOFBIRTH = preferencesKey<String>("DATEOFBIRTH")
        val EMAIL = preferencesKey<String>("EMAIL")
        val ID = preferencesKey<String>("ID")
        val STATUS = preferencesKey<String>("STATUS")

    }

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
    suspend fun update (username : String, address: String, complete: String, dateof: String){
        dataStore.edit {
            it[USERNAME] = username
            it[ADDRESS] = address
            it[COMPLETE] = complete
            it[DATEOFBIRTH] = dateof
        }
    }
    suspend fun logout(){
        dataStore.edit {
            it.clear()
        }
    }
    suspend fun setStatus(status : String){
        dataStore2.edit {
            it[STATUS] = status
        }
    }

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
}