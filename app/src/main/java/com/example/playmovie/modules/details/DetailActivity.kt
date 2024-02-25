package com.example.playmovie.modules.details

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.playmovie.R
import com.example.playmovie.databases.DatabaseContract
import com.example.playmovie.databases.MovieHelper
import com.example.playmovie.model.FilmModel
import com.example.playmovie.modules.home.MainActivity
import com.example.playmovie.modules.login.LoginActivity
import com.example.playmovie.utils.Const.CODE_LOGIN
import com.example.playmovie.utils.UserPreference


class DetailActivity : AppCompatActivity() {



    lateinit var userPreference : UserPreference
    lateinit var data : FilmModel

    private var values = ContentValues()
    lateinit var movieHelper : MovieHelper

    var statusFavorite : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        userPreference = UserPreference(this)
        data = intent.getParcelableExtra<FilmModel>("data")!!

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()

        initView()
        initListener()


    }

    fun initView() {
        val tv_title2 : TextView = findViewById(R.id.tv_title2)
        val tv_genre : TextView = findViewById(R.id.tv_genre)
        val tv_desc : TextView = findViewById(R.id.tv_desc)
        val imageView2 : ImageView = findViewById(R.id.imageView2)
        tv_title2.setText(data.title)
        tv_genre.setText(data.genre)
        tv_desc.setText(data.describe)

        statusFavorite()

        imageView2.setOnClickListener{
            finish()
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out)
        }
    }

    fun initListener() {
        val videoView : VideoView = findViewById(R.id.videoView)
        val iv_favorit : ImageView = findViewById(R.id.iv_favorit)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + data.trailer))
        videoView.start()

        iv_favorit.setOnClickListener {
            if(userPreference.getStatusUser()) {
                if (statusFavorite) {
                    movieHelper.deleteById(data.id.toString())
                    iconFavorite(false)
                } else {
                    values.put(DatabaseContract.movieColumns._ID, data.id)
                    values.put(DatabaseContract.movieColumns.TITLE, data.title)
                    values.put(DatabaseContract.movieColumns.DESC, data.describe)
                    values.put(DatabaseContract.movieColumns.GENRE, data.genre)
                    values.put(DatabaseContract.movieColumns.POSTER, data.poster)
                    values.put(DatabaseContract.movieColumns.TRAILER, data.trailer)
                    values.put(DatabaseContract.movieColumns.RATING, data.rating)

                    movieHelper.insert(values)

                    iconFavorite(true)
                }
            } else {
                val intent = Intent( this, LoginActivity::class.java )
                startActivityForResult(intent, CODE_LOGIN, null)
            }

        }
    }

    fun iconFavorite(status:Boolean) {
        val iv_favorit : ImageView = findViewById(R.id.iv_favorit)
        if (status) {
            statusFavorite = true
            iv_favorit.setImageResource(R.drawable.ic_baseline_favorite_enable)
        } else {
            statusFavorite = false
            iv_favorit.setImageResource(R.drawable.ic_baseline_favorite_disable)
        }
    }

    fun statusFavorite() {
        val cursor = movieHelper.queryById(data.id.toString())
        if (cursor.moveToNext()) {
            iconFavorite(true)
        } else {
            iconFavorite(false)
        }
    }

    // ------------------------------------------------------------------------
    // HOOKS INTO ACTIVITY
    // ------------------------------------------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, bundle: Intent?) {
        super.onActivityResult(requestCode, resultCode, bundle)
        if (requestCode == CODE_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                if (statusFavorite) {
                    movieHelper.deleteById(data.id.toString())
                    iconFavorite(false)
                } else {
                    values.put(DatabaseContract.movieColumns._ID, data.id)
                    values.put(DatabaseContract.movieColumns.TITLE, data.title)
                    values.put(DatabaseContract.movieColumns.DESC, data.describe)
                    values.put(DatabaseContract.movieColumns.GENRE, data.genre)
                    values.put(DatabaseContract.movieColumns.POSTER, data.poster)
                    values.put(DatabaseContract.movieColumns.TRAILER, data.trailer)
                    values.put(DatabaseContract.movieColumns.RATING, data.rating)

                    movieHelper.insert(values)

                    iconFavorite(true)
                }
            }
        }
    }
}