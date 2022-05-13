package com.binar.challenge5.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.binar.challenge5.R
import com.binar.challenge5.room.FavoriteDatabase
import com.binar.challenge5.model.DataFilmBaruItem
import com.binar.challenge5.model.Favorite
import com.binar.challenge5.viewmodel.ViewModelFilm
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DetailFilm : AppCompatActivity() {

    var dbFavorite : FavoriteDatabase? = null
    var cek : Int = 0
    lateinit var viewModel : ViewModelFilm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        dbFavorite = FavoriteDatabase.getInstance(this)


        val detailfilm = intent.getParcelableExtra<DataFilmBaruItem>("detailfilm")

        txtJudul.text = detailfilm?.name
        txtTanggal.text = detailfilm?.date
        txtSutradara.text = detailfilm?.director
        txtSinopsis.text = detailfilm?.description

        Glide.with(applicationContext).load(detailfilm?.image).into(imageFilm1)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ViewModelFilm::class.java)
        viewModel.cekData.observe(this, Observer {
            if (it != 0){
                btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                cek = 1

            }else{
                btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
                cek = 0
            }
        })
        viewModel.checkFav(detailfilm!!.id)


        btnFavorite2.setOnClickListener {
            if (cek == 0){
                GlobalScope.async {
                    viewModel.insertFav(Favorite(null, detailfilm?.id,
                        detailfilm?.name,
                        detailfilm?.image,
                        detailfilm?.director,
                        detailfilm?.description,
                        detailfilm?.date))
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Berhasil menambahkan ke Favorite Film", Toast.LENGTH_LONG).show()
                        btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                    }


                }
            }else{
                GlobalScope.async {
                    viewModel.deleteFav(detailfilm!!.id)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Berhasil menghapus dari Favorite Film", Toast.LENGTH_LONG).show()
                        btnFavorite2.setBackgroundResource(R.drawable.ic_baseline_star_border_24)

                    }
                }
            }
        }
    }
}