package com.example.gamewithpaging.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamewithpaging.model.TagsList

class GameImageListAdapter(private val tags:List<TagsList>) : RecyclerView.Adapter<GameDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailViewHolder {
        return GameDetailViewHolder.create(parent)
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: GameDetailViewHolder, position: Int) {
        val actor = tags[position]
        holder.bind(actor)
    }

}