package com.example.playmovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playmovie.R
import com.example.playmovie.model.FilmModel

class AllMovieAdaptor (private var data:List<FilmModel>,
                       private val listener: (FilmModel) -> Unit)
    : RecyclerView.Adapter<AllMovieAdaptor.MovieViewHolder>(){

    lateinit var ContextAdaptor : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdaptor = parent.context

        val inflatedView : View = layoutInflater.inflate(R.layout.item_movie_vertikal,parent, false)
        return MovieViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int){
        holder.bindItem(data[position], listener, ContextAdaptor, position)
    }

    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvTitle:TextView = view.findViewById(R.id.tv_title)
        private val ivposter:ImageView = view.findViewById(R.id.iv_poster)
        private val tvdesc:TextView = view.findViewById(R.id.tv_desc)
        private val rbMovie:RatingBar = view.findViewById(R.id.ratingBar)

        fun bindItem (data: FilmModel, listener: (FilmModel) -> Unit, context: Context, position: Int){
            tvTitle.text = data.title
            tvdesc.text = data.describe
            Glide.with(context)
                .load(data.poster)
                .into(ivposter)

            rbMovie.rating= data.rating

            itemView.setOnClickListener(View.OnClickListener {
                listener(data)
            }

            )
        }
    }


}
