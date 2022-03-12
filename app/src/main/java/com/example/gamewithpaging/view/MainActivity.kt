package com.example.gamewithpaging.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.example.gamewithpaging.view.db.DatabaseActivity
import com.example.gamewithpaging.view.network.NetworkOnlyActivity
import com.example.gamewithpaging.view.networkanddb.NetworkAndDatabaseActivity
import com.example.gamewithpaging.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.let {
            it.btnDatabase.setOnClickListener {
                startActivity(Intent(this, DatabaseActivity::class.java))
            }
            it.btnNetworkAndDatabase.setOnClickListener {
                startActivity(Intent(this, NetworkAndDatabaseActivity::class.java))
            }
            it.btnNetworkPage.setOnClickListener {
                startActivity(Intent(this, NetworkOnlyActivity::class.java))
            }
        }
    }
}