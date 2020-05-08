package com.lifehacktestapp.android.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lifehacktestapp.android.R

class ImageUtil {

    companion object {
        fun displayImage(context: Context, url: String, targetView: ImageView) {
            GlideApp.with(context).load(url)
                .diskCacheStrategy(
                    DiskCacheStrategy.ALL
                )
                .error(R.drawable.photo)
                .into(targetView);
        }
    }

}