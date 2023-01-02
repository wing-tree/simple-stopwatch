package com.wing.tree.bruni.simpleStopwatch.end.view

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.wing.tree.bruni.simpleStopwatch.end.databinding.ActivityMainBinding
import com.wing.tree.bruni.simpleStopwatch.end.viewModel.MainViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bind()
        viewModel.collect()
    }

    private fun ActivityMainBinding.bind() {
        play.setOnClickListener {
            viewModel.play()
        }

        pause.setOnClickListener {
            viewModel.pause()
        }

        stop.setOnClickListener {
            viewModel.stop()
        }
    }

    private fun MainViewModel.collect() {
        lifecycleScope.launch {
            val simpleDateFormat = SimpleDateFormat(PATTERN, Locale.getDefault())

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                @OptIn(FlowPreview::class)
                overallTime.sample(31.milliseconds).collect {
                    binding.overallTime.text = simpleDateFormat.format(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                state.collect {
                    with(binding) {
                        play.isEnabled = it.isPlayEnabled
                        pause.isEnabled = it.isPauseEnabled
                        stop.isEnabled = it.isStopEnabled
                    }
                }
            }
        }
    }

    companion object {
        private const val PATTERN = "mm:ss.SS"
    }
}
