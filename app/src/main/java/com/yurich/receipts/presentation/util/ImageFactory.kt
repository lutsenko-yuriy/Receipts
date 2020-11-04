package com.yurich.receipts.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

object ImageFactory {

    private val cache = mutableMapOf<String, Bitmap>()

    fun getImageFor(context: Context, uri: Uri) =
        cache[uri.toString()] ?: buildImageFor(context, uri)

    @Synchronized
    private fun buildImageFor(context: Context, uri: Uri) =
        cache[uri.toString()] ?: run {
            val imageStream = context.contentResolver.openInputStream(uri)
            cache[uri.toString()] = BitmapFactory.decodeStream(imageStream)

            cache[uri.toString()]!!
        }
}