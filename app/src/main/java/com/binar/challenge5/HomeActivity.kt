package com.binar.challenge5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challenge5.viewmodel.ViewModelFilm
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var adapterFilm : RvAdapter
    lateinit var sf : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sf = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)


        val username = sf.getString("USERNAME","")
        txtNama.text = username

        btnProfil.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
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
        sf = this.getSharedPreferences("datalogin", Context.MODE_PRIVATE)


        val username = sf.getString("USERNAME","")
        txtNama.text = username
    }
}