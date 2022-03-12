package com.example.gamewithpaging.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.gamewithpaging.data.GamesRepository
import com.example.gamewithpaging.databinding.GameDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity() {

    lateinit var bindingDetail: GameDetailBinding

    private val viewModel by viewModels<GameDetailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDetail = GameDetailBinding.inflate(layoutInflater)
        setContentView(bindingDetail.root)

//        lifecycleScope.launch {
//            viewModel.games.collectLatest {
//                gameAdapter.submitData(it)
//            }
//        }
    }
}