package com.binar.challenge5.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challenge5.R
import com.binar.challenge5.adapter.RvAdapter
import com.binar.challenge5.viewmodel.ViewModelFilm
import com.binar.challenge5.manager.UserManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var adapterFilm : RvAdapter
    //lateinit var sf : SharedPreferences
    lateinit var userManager : UserManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //sf = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        userManager = UserManager(this)
        userManager.userNAME.asLiveData().observe(this) {
            txtNama.text = it
        }

        //val username = sf.getString("USERNAME","")

        btnProfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))

        }

        getDataFilm2()
    }

    fun getDataFilm2(){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.getLiveFilmObserver().observe(this, {
            if (it != null){
                rv_item.layoutManager = LinearLayoutManager(this)
                adapterFilm = RvAdapter (){
                    val pindah = Intent(this@HomeActivity, DetailFilm::class.java)
                    pindah.putExtra("detailfilm", it)
                    startActivity(pindah)


                }
                rv_item.adapter = adapterFilm
                adapterFilm.setDataFilm(it)
                adapterFilm.notifyDataSetChanged()

            }

        })

        viewModel.makeApiFilm()

    }


    override fun onResume() {
        super.onResume()
        //sf = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)
        userManager.userNAME.asLiveData().observe(this, {
            txtNama.text = it
        })


        //val username = sf.getString("USERNAME","")
    }
}