package com.example.gamewithpaging.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gamewithpaging.R
import com.example.gamewithpaging.databinding.ItemGameDetailBinding
import com.example.gamewithpaging.model.TagsList

class GameDetailViewHolder(private val binding: ItemGameDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TagsList?) {
        binding.imageTag.load(item!!.imageBackground) {
            placeholder(R.drawable.ic_launcher_background)
        }
    }

    companion object {
        fun create(view: ViewGroup): GameDetailViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = ItemGameDetailBinding.inflate(inflater, view, false)
            return GameDetailViewHolder(binding)
        }
    }
}