package com.example.playmovie.databases

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class movieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "id"
            const val TITLE = "title"
            const val DESC = "describe"
            const val GENRE = "genre"
            const val POSTER = "poster"
            const val TRAILER = "trailer"
            const val RATING = "rating"
        }
    }
}