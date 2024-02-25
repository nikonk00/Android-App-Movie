package com.example.playmovie.modules.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playmovie.modules.lists.AllMovieActivity
import com.example.playmovie.R
import com.example.playmovie.adapter.MovieAdaptor
import com.example.playmovie.api.DummyData
import com.example.playmovie.model.FilmModel
import com.example.playmovie.modules.details.DetailActivity
import com.example.playmovie.modules.login.LoginActivity
import com.example.playmovie.modules.wishlist.WishlistActivity
import com.example.playmovie.utils.Const
import com.example.playmovie.utils.UserPreference
import com.facebook.shimmer.ShimmerFrameLayout



class MainActivity : AppCompatActivity() {

    private  var dataList = ArrayList<FilmModel>()
    lateinit  var userPreference : UserPreference
    private var dataOffline = ArrayList<FilmModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv_shimmer : ShimmerFrameLayout = findViewById(R.id.rv_shimmer)
        val rv_movie : RecyclerView = findViewById(R.id.rv_movie)

        userPreference = UserPreference(this)
        rv_shimmer.startShimmerAnimation()

        Handler().postDelayed({
            rv_shimmer.stopShimmerAnimation()
            rv_shimmer.visibility = View.GONE
            rv_movie.visibility = View.VISIBLE
            initListener()
            getData()
        }, 3000)
    }

    private fun initListener() {
        val tv_user : TextView = findViewById(R.id.tv_user)

        tv_user.setOnClickListener {
            if(userPreference.getStatusUser()) {
                startActivity(Intent(this, WishlistActivity::class.java))
            } else {
                val intent = Intent( this, LoginActivity::class.java )
                startActivityForResult(intent, Const.CODE_LOGIN, null)
            }
        }
    }

    private fun initView(){
        val rv_movie : RecyclerView = findViewById(R.id.rv_movie)
        val textView2 : TextView = findViewById((R.id.textView2))
        val tv_user : TextView = findViewById((R.id.tv_user))
        val tv_desc : TextView = findViewById((R.id.tv_desc))
        val tv_favorite : TextView = findViewById((R.id.tv_favorite))
        val iv_logout : ImageView = findViewById((R.id.iv_logout))

        rv_movie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rv_movie.adapter = MovieAdaptor(dataList){
            val intent = Intent(this, DetailActivity::class.java)
                .putExtra("data", it)
            startActivity(intent)
        }

        textView2.setOnClickListener {
            val intent = Intent(
                this,
                AllMovieActivity::class.java
            ).putExtra("data", dataList)
            startActivity(intent)
        }

        if (userPreference.getStatusUser()) {
            tv_user.text = userPreference.getNamaUser()
            tv_desc.text = "Thanks for join, do you want exit ?"
            tv_favorite.text = dataOffline.size.toString()
            iv_logout.visibility = View.VISIBLE
        } else {
            tv_user.text = "Login"
            tv_desc.text = "Save your favorite movie"
            iv_logout.visibility = View.INVISIBLE
        }
    }

    fun getData() {
        for ( i in DummyData.titleMovie.indices) {
            dataList.add(
                FilmModel(
                    i+1,
                    DummyData.titleMovie[i],
                    DummyData.descMovie[i],
                    DummyData.genreMovie[i],
                    DummyData.posterMovie[i],
                    DummyData.trailerMovie[i],
                    DummyData.ratingMovie[i]
                )
            )
        }

        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Const.CODE_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                val intent = Intent( this, WishlistActivity::class.java )
                startActivity(intent)
                initView()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        initView()
    }
}