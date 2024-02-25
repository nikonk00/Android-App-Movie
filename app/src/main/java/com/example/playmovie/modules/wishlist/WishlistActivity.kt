package com.example.playmovie.modules.wishlist

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playmovie.R
import com.example.playmovie.adapter.AllMovieAdaptor
import com.example.playmovie.databases.MovieHelper
import com.example.playmovie.model.FilmModel
import com.example.playmovie.modules.details.DetailActivity
import com.example.playmovie.utils.Const
import com.example.playmovie.utils.MappingHelper
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WishlistActivity : AppCompatActivity() {

    private var dataList = ArrayList<FilmModel>()
    lateinit var movieHelper : MovieHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movie)
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        movieHelper = MovieHelper.getInstance(this)
        movieHelper.open()


        toolbar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out)
        }

    }

    fun getData(){
        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovies = async(Dispatchers.IO) {
                var cursor = movieHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            dataList = deferredMovies.await()
            viewInit()
        }
    }

    fun viewInit(){
        val rv_movie : RecyclerView = findViewById(R.id.rv_movie)

        rv_movie.layoutManager = LinearLayoutManager(applicationContext)

            rv_movie.adapter = AllMovieAdaptor(dataList) {
                val intent = Intent(
                    this,
                    DetailActivity::class.java
                ).putExtra(Const.intentData, it)

                startActivity(intent)
            }

        }

    override fun onResume() {
        super.onResume()

        getData()
    }
}
