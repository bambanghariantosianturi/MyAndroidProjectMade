package com.example.myandroidproject.core.utils.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myandroidproject.core.BuildConfig
import com.example.myandroidproject.core.R
import com.example.myandroidproject.core.databinding.ItemListMovieBinding
import com.example.myandroidproject.core.domain.model.listmoviesmodel.MovieItemModel

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private var listData = ArrayList<MovieItemModel>()
    var onItemClick: ((MovieItemModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<MovieItemModel>?) {
        if (data == null) return
        listData.addAll(data)
        notifyDataSetChanged()
//        notifyItemRangeInserted((listData.size - data.size), data.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        listData.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListMovieBinding.bind(itemView)
        fun bind(data: MovieItemModel) {
            with(binding) {
                Glide.with(ivMovie.context)
                    .load(BuildConfig.IMG_URL + data.poster_path)
                    .placeholder(R.drawable.ic_movies_24)
                    .apply(RequestOptions.fitCenterTransform())
                    .into(ivMovie)
                tvTitleMovie.text = data.title
                tvRating.bindRatingPrefix((data.vote_average).toString())

                if (data.isFavorite) {
                    binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(binding.root.context, com.example.myandroidproject.core.R.drawable.ic_favorite_black_24))
                } else {
                    binding.ivFavorite.setImageDrawable(ContextCompat.getDrawable(binding.root.context, com.example.myandroidproject.core.R.drawable.ic_favorite_border_24))
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                val a = listData[adapterPosition]
                print(a)
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

}

fun AppCompatTextView.bindRatingPrefix(rating: String?) {
    rating?.let {
        text = String.format(
            this.context.getString(R.string.movie_rating),
            rating
        )
    }
}