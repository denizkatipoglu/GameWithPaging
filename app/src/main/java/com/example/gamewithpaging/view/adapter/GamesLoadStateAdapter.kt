package com.example.gamewithpaging.view.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GamesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<GamesLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: GamesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GamesLoadStateViewHolder {
        return GamesLoadStateViewHolder.create(parent, retry)
    }
}
