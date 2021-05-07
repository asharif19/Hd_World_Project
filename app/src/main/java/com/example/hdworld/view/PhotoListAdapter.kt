package com.example.hdworld.view

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hdworld.R
import com.example.hdworld.model.PhotoData
import com.kc.unsplash.models.Photo
import com.squareup.picasso.Picasso

class PhotoListAdapter(
    private var photos: ArrayList<PhotoData>,
    var clickListner: OnPhotoItemClickListner
) :
    RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {
    fun updatePhotos(newPhotos: List<PhotoData>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
    )


    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        //holder.bind(photos[position])
        holder.init(photos[position], clickListner)
    }


    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val PhotoName = view.findViewById<TextView>(R.id.BreedTxt)
        private val imageView = view.findViewById<ImageView>(R.id.imageViewUp)
        private val Likes = view.findViewById<TextView>(R.id.likes)
        private val location = view.findViewById<TextView>(R.id.LocationTxt)
        private val description = view.findViewById<TextView>(R.id.DesTxt)


        /* fun bind(photo: PhotoData) {
             Likes.text = photo.Likes
             PhotoName.text = photo.user?.name
             location.text = photo.user?.location
             Picasso.get().load(photo.urls?.regular).into(imageView)
         }*/

        fun init(photo: PhotoData, action: OnPhotoItemClickListner) {
            Likes.text = photo.Likes
            PhotoName.text = photo.user?.name
            location.text = "" + photo.user?.location
            Picasso.get().load(photo.urls?.regular).into(imageView)
            description.text = photo.Alt_description

            itemView.setOnClickListener {
                action.onItemClick(photo, adapterPosition)
            }
        }
    }

    interface OnPhotoItemClickListner {
        fun onItemClick(photo: PhotoData, position: Int)
    }
}