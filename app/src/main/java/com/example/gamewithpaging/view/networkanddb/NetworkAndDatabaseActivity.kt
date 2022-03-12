package com.example.gamewithpaging.view.networkanddb

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
class NetworkAndDatabaseActivity : BaseGameActivity() {

    override val viewModel: NetworkAndDbViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter(true)
        lifecycleScope.launch {
            viewModel.games.collectLatest {
                gameAdapter.submitData(it)
            }
        }
    }
}