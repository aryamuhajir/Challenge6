package com.binar.challenge5.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.binar.challenge5.R
import com.binar.challenge5.viewmodel.ViewModelUser
import com.binar.challenge5.manager.UserManager
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    lateinit var viewModel: ViewModelUser
    lateinit var userManager : UserManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var id : Int = 0


        userManager = UserManager(this)
        userManager.userNAME.asLiveData().observe(this) {
            updateUsername.setText(it)
            userManager.userALAMAT.asLiveData().observe(this) {
                updateAlamat.setText(it)
                userManager.userCOMPLETE.asLiveData().observe(this) {
                    updateLengkap.setText(it)
                    userManager.userTANGGAL.asLiveData().observe(this) {
                        updateTanggal.setText(it)
                        userManager.userID.asLiveData().observe(this) {
                            id = it.toInt()

                        }
                    }

                }
            }
        }

        imageBtn.setOnClickListener {
            setImage()
        }

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

    private fun setImage() {
        val inten = Intent(Intent.ACTION_PICK)
        inten.type = "image/*"

        startActivityForResult(inten, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode === RESULT_OK) {
            imageBtn.setImageURI(data?.data)
        }
    }

    fun update(id : Int, username : String, completeName :String, dateofbirth : String, address : String){
        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)
        viewModel.getLiveUserObserver().observe(this, Observer {
            GlobalScope.launch {
                userManager.update(username,address,completeName,dateofbirth)
            }

            Toast.makeText(this, "Berhasil update data", Toast.LENGTH_LONG).show()
        })
        viewModel.updateUser(id, username, completeName, dateofbirth, address)
    }


    fun logout(){
        GlobalScope.launch {
            userManager.logout()
            userManager.setStatus("no")
        }
        startActivity(Intent(this, MainActivity::class.java))
    }
}