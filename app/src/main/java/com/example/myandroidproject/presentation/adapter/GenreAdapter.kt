package com.example.myandroidproject.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myandroidproject.core.R
import com.example.myandroidproject.core.databinding.ItemListUserBinding
import com.example.myandroidproject.core.domain.model.genremoviemodel.GenreItemModel


class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ListViewHolder>() {
    private var listData = ArrayList<GenreItemModel>()
    var onItemClick: ((GenreItemModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setDataGenre(data: List<GenreItemModel>?) {
        if (data == null) return
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
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
        private val binding = ItemListUserBinding.bind(itemView)
        fun bind(data: GenreItemModel) {
            binding.tvName.text = data.name
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }
}