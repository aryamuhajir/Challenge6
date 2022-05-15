package com.binar.challenge5.view

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.binar.challenge5.R
import com.binar.challenge5.viewmodel.ViewModelUser
import com.binar.challenge5.manager.UserManager
import com.bumptech.glide.Glide
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
                            userManager.userIMAGE.asLiveData().observe(this){
                                Glide.with(this).load(it).into(imageBtn)

                                //imageBtn.setImageURI(it.toUri())
                            }

                        }
                    }

                }
            }
        }

        btnPick.setOnClickListener {
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

        if (requestCode == 100 && resultCode === RESULT_OK && data != null) {
            imageBtn.setImageURI(data?.data)
            GlobalScope.launch {
                userManager.setImage(data?.data.toString())
            }
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
    fun isPermissionsAllowed(): Boolean {
        return if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }

    fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2000)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2000 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    //  askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", "com.binar.challengechapterenam", null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
    }


}