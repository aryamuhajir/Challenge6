package com.binar.challenge5

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challenge5.model.Responseuser
import com.binar.challenge5.network.APIClient
import com.binar.challenge5.viewmodel.ViewModelFilm
import com.binar.challenge5.viewmodel.ViewModelUser
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    lateinit var sf : SharedPreferences
    lateinit var viewModel: ViewModelUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        sf = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)

        val username = sf.getString("USERNAME","")
        val alamat = sf.getString("ALAMAT","")
        val id = sf.getString("ID","")!!.toInt()
        val namaLengkap = sf.getString("NAMALENGKAP","")
        val tanggal = sf.getString("TANGGALLAHIR","")

        updateUsername.setText(username)
        updateAlamat.setText(alamat)
        updateLengkap.setText(namaLengkap)
        updateTanggal.setText(tanggal)

        btnUpdate.setOnClickListener {
            val username1 = updateUsername.text.toString()
            val namalengkap1 = updateLengkap.text.toString()
            val alamat1 = updateAlamat.text.toString()
            val tanggal1 = updateTanggal.text.toString()

            update(id, username1, namalengkap1, alamat1, tanggal1)
        }

        btnLogout.setOnClickListener {
            val a = AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Apakah yakin Log out dari aplikasi?")
                .setPositiveButton("YA") { dialog: DialogInterface, i: Int ->
                    logout()
                }
                .setNegativeButton("TIDAK") {dialog : DialogInterface, i : Int ->
                    dialog.dismiss()
                }
                .show()
        }



    }
    fun update(id : Int, username : String, completeName :String, dateofbirth : String, address : String){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(this, Observer {
            sf = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)

            val sfedit = sf.edit()
            sfedit.putString("USERNAME", username)
            sfedit.putString("ALAMAT", address)
            sfedit.putString("TANGGALLAHIR", dateofbirth)
            sfedit.putString("NAMALENGKAP", completeName)
            sfedit.apply()

            Toast.makeText(this, "Berhasil update data", Toast.LENGTH_LONG).show()


        })
        viewModel.updateUser(id, username, completeName, dateofbirth, address)




    }

//    fun update(id : String, username : String, nameLengkap : String, alamat : String, tanggal : String){
//        APIClient.instance.updateUser(id.toInt(), username, nameLengkap, alamat, tanggal)
//            .enqueue(object : Callback<List<Responseuser>>{
//                override fun onResponse(
//                    call: Call<List<Responseuser>>,
//                    response: Response<List<Responseuser>>
//                ) {
//                    if (response.isSuccessful){
//
//                        Toast.makeText(this@ProfileActivity, "Berhasil memperbarui data", Toast.LENGTH_LONG).show()
//                    }else{
//                        Toast.makeText(this@ProfileActivity, "Error", Toast.LENGTH_LONG).show()
//
//                    }
//
//
//                }
//
//                override fun onFailure(call: Call<List<Responseuser>>, t: Throwable) {
//                    val nama = sf.edit()
//                    nama.putString("USERNAME", updateUsername.text.toString())
//                    nama.apply()
//
//                    Toast.makeText(this@ProfileActivity, t.message, Toast.LENGTH_LONG).show()
//                }
//
//            })
//
//    }

    fun logout(){
        val logoutsf = sf.edit()
        logoutsf.clear()
        logoutsf.apply()
        startActivity(Intent(this, MainActivity::class.java))
    }


}