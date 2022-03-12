package com.example.gamewithpaging.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamewithpaging.R
import com.example.gamewithpaging.model.TagsList

class GameImageListAdapter(private val actors:List<TagsList>) : RecyclerView.Adapter<GameDetailViewHolder>() {

//    private var actors: List<TagsList> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameDetailViewHolder {
        return GameDetailViewHolder.create(parent)
    }

    override fun getItemCount(): Int = actors.size

    override fun onBindViewHolder(holder: GameDetailViewHolder, position: Int) {
        val actor = actors[position]
        holder.bind(actor)
    }

//    fun submitList(listActors: List<TagsList>) {
//        this.actors = listActors
//        notifyDataSetChanged()
//    }
}