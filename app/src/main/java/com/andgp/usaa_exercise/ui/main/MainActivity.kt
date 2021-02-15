package com.andgp.usaa_exercise.ui.main

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andgp.usaa_excercise.R
import com.andgp.usaa_excercise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(this.application)
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.lifecycleOwner = this@MainActivity
        binding.viewModel = viewModel
        binding.callback = callback
    }

    override fun onStart() {
        super.onStart()
        setupObservers()
        viewModel.initComponents()
    }

    private fun setupObservers() {
        viewModel.loadingStatus.observe(this, { visible ->
            binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
        })
    }

    private val callback = object : MainActivityCallback {
        override fun launchSearch() {
            viewModel.tryObtainData()
        }
    }
}

interface MainActivityCallback {
    fun launchSearch()
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}