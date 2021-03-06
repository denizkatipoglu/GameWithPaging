package com.example.gamewithpaging.view.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gamewithpaging.R
import com.example.gamewithpaging.databinding.ItemGameBinding
import com.example.gamewithpaging.model.GameResults

class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GameResults?, mlistener: GamesAdapter.RecyclerViewClickListener) {
        binding.imageGame.load(item!!.backgroundImage!!) {
            placeholder(R.drawable.ic_launcher_background)
        }
        binding.textGame.text = item.name
        if (item.rating!!.isNotEmpty()) {
            binding.textRating.text = item.rating
        } else {
            binding.textRating.text = "-"
        }

        if (!TextUtils.isEmpty(item.releasedDate)){
            binding.textDate.text = item.releasedDate
        }else{
            binding.textDate.text = "-"
        }
        binding.imageGame.setOnClickListener {
            mlistener.onItemClick(item)
        }

    }

    companion object {
        fun create(view: ViewGroup): GameViewHolder {
            val inflater = LayoutInflater.from(view.context)
            val binding = ItemGameBinding.inflate(inflater, view, false)
            return GameViewHolder(binding)
        }
    }
}