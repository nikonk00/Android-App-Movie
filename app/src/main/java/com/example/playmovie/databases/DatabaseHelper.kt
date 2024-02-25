package com.example.playmovie.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.playmovie.databases.DatabaseContract.movieColumns;
import com.example.playmovie.databases.DatabaseContract.movieColumns.Companion.TABLE_NAME

internal class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "dbmovie_app"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE note" +
                " (${movieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${movieColumns.TITLE} TEXT NOT NULL," +
                " ${movieColumns.DESC} TEXT NOT NULL," +
                " ${movieColumns.GENRE} TEXT NOT NULL," +
                " ${movieColumns.POSTER} TEXT NOT NULL," +
                " ${movieColumns.TRAILER} TEXT NOT NULL," +
                " ${movieColumns.RATING} TEXT NOT NULL)"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase , p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}