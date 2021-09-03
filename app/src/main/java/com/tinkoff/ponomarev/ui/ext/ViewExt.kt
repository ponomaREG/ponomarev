package com.tinkoff.ponomarev.ui.ext

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.visible(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
}

fun ImageView.loadGif(gifUrl: String){
    Glide.with(this)
        .asGif()
        .load(gifUrl)
        .into(this)

}