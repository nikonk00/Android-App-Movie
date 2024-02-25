package com.example.playmovie.utils

import android.database.Cursor
import com.example.playmovie.databases.DatabaseContract
import com.example.playmovie.model.FilmModel

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FilmModel>{
        val notesList = ArrayList<FilmModel>()
        notesCursor?.apply{
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.movieColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.movieColumns.TITLE))
                val describe = getString(getColumnIndexOrThrow(DatabaseContract.movieColumns.DESC))
                val genre = getString(getColumnIndexOrThrow(DatabaseContract.movieColumns.GENRE))
                val poster = getInt(getColumnIndexOrThrow(DatabaseContract.movieColumns.POSTER))
                val trailer = getInt(getColumnIndexOrThrow(DatabaseContract.movieColumns.TRAILER))
                val rate = getFloat(getColumnIndexOrThrow(DatabaseContract.movieColumns.RATING))
                notesList.add(FilmModel(id, title, describe , genre, poster, trailer, rate))

            }
        }
        return notesList
    }
}