package com.example.laba__4

import android.graphics.Bitmap

class Album(
    val id: String,
    val owner_id: String,
    val title: String,
    val size: String,
    val thumb_src: String,
    var thumb: Bitmap
)