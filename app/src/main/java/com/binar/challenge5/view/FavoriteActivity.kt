package com.binar.challenge5.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModel.allFav.observe(this, Observer { list ->
            list?.let {
                if (it != null){
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

                }else{
                    statusTxt.text = "BELOOM ADA FILM FAVORITE"

                }

            }
        })
//        viewModel.getLiveFavObserver().observe(this){
//            if (it != null){
//                rv_fav.layoutManager = LinearLayoutManager(this)
//                adapterFavorite = FavAdapter (){
//                    val pindah = Intent(this@FavoriteActivity, DetailFilm::class.java)
//                    pindah.putExtra("detailfilm", it)
//                    startActivity(pindah)
//                }
//
//                rv_fav.adapter = adapterFavorite
//                adapterFavorite.setDataFilm(it!!)
//                adapterFavorite.notifyDataSetChanged()
//            }else{
//
//            }
//        }
//        viewModel.getFavItem()




    }
}