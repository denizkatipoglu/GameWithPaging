package com.example.gamewithpaging.view.db

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.gamewithpaging.core.BaseGameActivity
import com.example.gamewithpaging.databinding.ActivityGamesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class DatabaseActivity : BaseGameActivity() {

    override val viewModel: DataBaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        viewModel.fillWithDummyGames()
        lifecycleScope.launch {
            viewModel.games.collectLatest {
                gameAdapter.submitData(it)
            }
        }
    }
}