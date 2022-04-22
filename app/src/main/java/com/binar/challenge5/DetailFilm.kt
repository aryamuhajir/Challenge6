package com.binar.challenge5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.binar.challenge5.model.GetAllFilmResponseItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_film.*

class DetailFilm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val detailfilm = intent.getParcelableExtra<GetAllFilmResponseItem>("detailfilm")

        txtJudul.text = detailfilm?.title
        txtTanggal.text = detailfilm?.createdAt
        txtSutradara.text = detailfilm?.director
        txtSinopsis.text = detailfilm?.synopsis

        Glide.with(applicationContext).load(detailfilm?.image).into(imageFilm1)



    }
}