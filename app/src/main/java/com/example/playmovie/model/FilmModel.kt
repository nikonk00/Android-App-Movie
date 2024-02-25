package com.example.playmovie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel (
    var id: Int ?= 0,
    var title: String ?="",
    var describe: String ?="",
    var genre: String ?="",
    var poster: Int ?= 0,
    var trailer: Int ?= 0,
    var rating: Float
): Parcelable