package com.example.gamewithpaging.view.gamedetail

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.gamewithpaging.Constants
import com.example.gamewithpaging.R
import com.example.gamewithpaging.databinding.GameDetailBinding
import com.example.gamewithpaging.model.GameDetailModel
import com.example.gamewithpaging.model.GameResults
import com.example.gamewithpaging.view.adapter.GameImageListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailActivity : AppCompatActivity() {

    private lateinit var bindingDetail: GameDetailBinding
    private val viewModel by viewModels<GameDetailViewModel>()

    private lateinit var gameImageAdapter: GameImageListAdapter

    private var gameResults: GameResults? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDetail = GameDetailBinding.inflate(layoutInflater)
        setContentView(bindingDetail.root)

        gameResults = intent.getSerializableExtra(Constants.GAME_DETAIL) as GameResults

        observeData()
        viewModel.loadGameDetail(gameResults!!.id)
    }

    private fun observeData() {
        viewModel.getGameDetail().observe(this, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(resource: GameDetailModel?) {
        if (resource!!.genres!!.isNotEmpty()) {
            val stringCommaGnre = resource.genres!!.joinToString { it.name }
            bindingDetail.tvGnreValue.text = stringCommaGnre
        }
        if (resource.added!!.isNotEmpty()) {

            bindingDetail.tvAddedCount.text = resource.added
        }
        if (resource.description.isNotEmpty()) {
            bindingDetail.tvDescriptionValue.visibility = View.VISIBLE
            bindingDetail.tvDescriptionTitle.visibility = View.VISIBLE
            bindingDetail.tvDescriptionValue.text = Html.fromHtml(resource.description, 0)
        } else {
            bindingDetail.tvDescriptionValue.visibility = View.GONE
            bindingDetail.tvDescriptionTitle.visibility = View.GONE
        }

        bindingDetail.ivPoster.load(resource.backgroundImage!!) {
            placeholder(R.drawable.ic_launcher_background)
        }

        bindingDetail.ivBackdrop.load(resource.backgroundImage) {
            placeholder(R.drawable.ic_launcher_background)
        }
        if (resource.rating!!.isNotEmpty()) {
            bindingDetail.tvVoteAverage.text = resource.rating
        }

        if (resource.name.isNotEmpty()) {
            bindingDetail.tvDescriptionTitle.text = resource.name
        }

        gameImageAdapter = GameImageListAdapter(resource.tags!!)
        bindingDetail.rvImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bindingDetail.rvImages.adapter = gameImageAdapter
    }

    fun <A, B> zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
        return MediatorLiveData<Pair<A, B>>().apply {
            var lastA: A? = null
            var lastB: B? = null

            fun update() {
                val localLastA = lastA
                val localLastB = lastB
                if (localLastA != null && localLastB != null)
                    this.value = Pair(localLastA, localLastB)
            }

            addSource(a) {
                lastA = it
                update()
            }
            addSource(b) {
                lastB = it
                update()
            }
        }
    }


}