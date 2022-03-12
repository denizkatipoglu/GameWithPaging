package com.example.gamewithpaging.view.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gamewithpaging.model.GameResults

class GamesAdapter(var mItemClickListener:RecyclerViewClickListener) : PagingDataAdapter<GameResults, RecyclerView.ViewHolder>(COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClick(selectedGame: GameResults)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GameResults -> ViewType.GAME.ordinal
            null -> throw UnsupportedOperationException("Unexpected View")
            else -> throw UnsupportedOperationException("Unexpected View")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when (it) {
                is GameResults -> (holder as? GameViewHolder)?.bind(it,mItemClickListener)
                else -> null
            }
        }
    }

    companion object {
        private enum class ViewType {
            GAME
        }

        private val COMPARATOR = object : DiffUtil.ItemCallback<GameResults>() {
            override fun areItemsTheSame(oldItem: GameResults, newItem: GameResults): Boolean {
                return compareGame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: GameResults, newItem: GameResults): Boolean =
                oldItem == newItem
        }


        private fun compareGame(
            oldItem: GameResults,
            newItem: GameResults
        ) = (oldItem is GameResults && newItem is GameResults &&
                oldItem.id == newItem.id)
    }
}