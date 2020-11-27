package com.android.myapplication.movies.ui.detail

import android.R.attr.fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.MovieImagesItemBinding
import com.android.myapplication.movies.models.get_photo_obj.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class SlideImagesAdapter(val photos: ArrayList<Photo>) :
    RecyclerView.Adapter<SlideImagesAdapter.VH>() {
    class VH(itemView: MovieImagesItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: MovieImagesItemBinding

        init {
            binding = itemView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val binding: MovieImagesItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.movie_images_item,
                parent,
                false
            )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val url = "https://farm" + photos.get(position).farm +
                ".static.flickr.com/" + photos.get(position).server +
                "/" + photos.get(position).id + "_"+photos.get(position).secret+".jpg"


        val myOptions = RequestOptions()
            .override(100, 100)

        Glide.with(holder.binding.images.context)
            .asBitmap()
            .apply(myOptions)
            .load(url)
            .into(holder.binding.images)


//        Glide.with(holder.binding.images.context)
//            .load(url).into(holder.binding.images)

    }

    override fun getItemCount() = photos.size
    fun updatePhotos(data: List<Photo>?) {
//        if (photos.size > 0) {
//            photos.clear()
//        }
        data?.let { photos.addAll(data) }
        notifyDataSetChanged()
    }
}