package com.jaehl.spacex.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter("bind:imageUrl","bind:placeholder")
    @JvmStatic fun setImage(image: ImageView, imageUrl: String?, placeholder: Drawable?) {
        if (!imageUrl.isNullOrEmpty()) {

            Glide.with(image.context).load(imageUrl).centerCrop()
                .placeholder(placeholder)
                .into(image)
        } else {
            image.setImageDrawable(placeholder)
        }
    }
}