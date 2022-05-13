package com.binar.challenge5.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challenge5.R
import com.binar.challenge5.adapter.FavAdapter
import com.binar.challenge5.adapter.RvAdapter
import com.binar.challenge5.model.DataFilmBaruItem
import com.binar.challenge5.viewmodel.ViewModelFilm
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FavoriteActivity : AppCompatActivity() {
    lateinit var adapterFavorite : FavAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        getFav()

    }
    fun getFav(){
        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.allFav.observe(this, Observer { list ->
            list?.let {
                if (it.size >= 1){
                    rv_fav.layoutManager = LinearLayoutManager(this)
                    adapterFavorite= FavAdapter (){
                        val pindah = Intent(this@FavoriteActivity, DetailFilm::class.java)
                        pindah.putExtra("detailfilm", DataFilmBaruItem(
                            it.date!!,it.description!!,it.director!!,it.id!!, it.image!!, it.name!!
                        ))
                        startActivity(pindah)
                    }
                    rv_fav.adapter = adapterFavorite
                    adapterFavorite.setDataFilm(it!!)
                    adapterFavorite.notifyDataSetChanged()
                    rv_fav.visibility = View.VISIBLE
                }else{
                    rv_fav.visibility = View.INVISIBLE
                    statusTxt.text = "BELUM ADA FILM FAVORITE"
                }
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        getFav()
    }
}