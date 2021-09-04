package com.tinkoff.ponomarev.ui.ext

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.gif.GifDrawable

/**
 * Функция-расширение, которая определяет текущую видимость вьюшки
 * @param isVisible - видима ли
 */
fun View.visible(isVisible: Boolean){
    visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
}

/**
 * Функция-расширение, которая загружает и отображает гиф-изоражение в [ImageView]
 * с помощью [Glide]
 * @param gifUrl - url-ссылка на изображение
 */
fun ImageView.loadGif(gifUrl: String){
    Glide.with(this)
        .asGif()
        .load(gifUrl)
        .attachProgressDrawable(this.context)
        .into(this)

}

/**
 * Фукнция-расширение, которая в качестве placeholder'a передает индикатор загрузки
 * Необходима для отображения загрузки гиф-изображения непосредственно в [ImageView]
 * @param context - контекст
 */
@SuppressLint("CheckResult")
fun RequestBuilder<GifDrawable>.attachProgressDrawable(context: Context) = apply {
    val circularProgressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 4f
        centerRadius = 45f
        start()
    }
    placeholder(circularProgressDrawable)
}