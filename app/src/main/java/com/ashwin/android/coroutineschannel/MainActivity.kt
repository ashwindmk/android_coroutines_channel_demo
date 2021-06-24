package com.ashwin.android.coroutineschannel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ashwin.android.coroutineschannel.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect {
                // Channel will not send/receive the last when restarted
                Log.d("coroutines-channel", "event: $it")
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.liveData.observe(this@MainActivity, Observer {
                // LiveData will send/receive the last value when restarted
                Log.d("coroutines-channel", "data: $it")
            })
        }

        binding.testButton.setOnClickListener {
            test()
        }
    }

    private fun test() {
        viewModel.triggerEvent()
        viewModel.triggerData()
    }
}
