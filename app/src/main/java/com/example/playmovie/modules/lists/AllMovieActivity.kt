package com.example.playmovie.modules.lists

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playmovie.R
import com.example.playmovie.adapter.AllMovieAdaptor
import com.example.playmovie.model.FilmModel
import com.example.playmovie.modules.details.DetailActivity


class AllMovieActivity : AppCompatActivity() {

    private var dataList = ArrayList<FilmModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movie)
        setSupportActionBar(findViewById(R.id.toolbar))

        dataList = intent.getParcelableArrayListExtra("data")!!

        val rv_movie:RecyclerView = findViewById(R.id.rv_movie)

        rv_movie.layoutManager = LinearLayoutManager(applicationContext)

        rv_movie.adapter = AllMovieAdaptor(dataList) {
            val intent = Intent(
                this,
                DetailActivity::class.java
            ).putExtra("data", it)

            startActivity(intent)
        }

        val toolbar : Toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.no_animation, R.anim.slide_out)
        }
    }
}