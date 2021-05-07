package com.example.hdworld.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.hdworld.R
import com.example.hdworld.model.PhotoData
import com.squareup.picasso.Picasso

class PhotoDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_detail_photo)

        val Likes = findViewById<TextView>(R.id.LikesTxt)
        val PhotoName = findViewById<TextView>(R.id.BreedTxt)
        val location = findViewById<TextView>(R.id.LocationsTxt)
        var imageView = findViewById<ImageView>(R.id.ImageViewUp)
        val description = findViewById<TextView>(R.id.DesTxt)

        Likes.text = intent.getStringExtra("likes")+" likes"
        PhotoName.text = intent.getStringExtra("name")
        location.text = "Location: " + intent.getStringExtra("location")
        intent.getStringExtra("urls")?.let { Picasso.get().load(it).into(imageView) }
        description.text = intent.getStringExtra("alt_description")
    }
}