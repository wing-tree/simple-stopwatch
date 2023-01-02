package com.wing.tree.bruni.simpleStopwatch.end.viewModel

import androidx.lifecycle.ViewModel
import com.wing.tree.bruni.simpleStopwatch.end.constant.TEN
import com.wing.tree.bruni.simpleStopwatch.end.constant.ZERO
import com.wing.tree.bruni.simpleStopwatch.end.stopwatch.Stopwatch
import com.wing.tree.bruni.simpleStopwatch.end.view.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _overallTime = MutableStateFlow(ZERO)
    val overallTime: StateFlow<Long> get() = _overallTime

    private val _state = MutableStateFlow<MainState>(MainState.Stop)
    val state: StateFlow<MainState> get() = _state

    private val stopwatch = Stopwatch(
        DELAY,
        PERIOD,
        object : Stopwatch.Callback {
            override fun onStart() {
                _state.update { MainState.Play }
            }

            override fun onStop() {
                _state.update { MainState.Pause }
            }

            override fun onReset() {
                _overallTime.update { ZERO }
                _state.update { MainState.Stop }
            }

            override fun onTick(period: Long) {
                updateTime(period)
            }
        }
    )

    private fun updateTime(period: Long) {
        _overallTime.update { it.plus(period) }
    }

    fun play() {
        stopwatch.start()
    }

    fun pause() {
        stopwatch.stop()
    }

    fun stop() {
        stopwatch.reset()
    }

    companion object {
        private const val DELAY = ZERO
        private const val PERIOD = TEN
    }
}
