package com.example.gamewithpaging.core

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.gamewithpaging.view.adapter.GamesLoadStateAdapter
import com.example.gamewithpaging.databinding.ActivityGamesBinding
import com.example.gamewithpaging.view.adapter.GamesAdapter

abstract class BaseGameActivity : AppCompatActivity() {
    abstract val viewModel: BaseViewModel
    lateinit var binding: ActivityGamesBinding
    val gameAdapter by lazy { GamesAdapter() }

    fun initAdapter(isMediator: Boolean = false) {
        binding.recyclerView.adapter = gameAdapter.withLoadStateHeaderAndFooter(
            header = GamesLoadStateAdapter { gameAdapter.retry() },
            footer = GamesLoadStateAdapter { gameAdapter.retry() }
        )


        gameAdapter.addLoadStateListener { loadState ->
            val refreshState =
                if (isMediator) {
                    loadState.mediator?.refresh
                } else {
                    loadState.source.refresh
                }
            binding.recyclerView.isVisible = refreshState is LoadState.NotLoading
            binding.progressBar.isVisible = refreshState is LoadState.Loading
            binding.buttonRetry.isVisible = refreshState is LoadState.Error
            handleError(loadState)
        }
        binding.buttonRetry.setOnClickListener {
            gameAdapter.retry()
        }
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(this, "${it.error}", Toast.LENGTH_LONG).show()
        }
    }
}