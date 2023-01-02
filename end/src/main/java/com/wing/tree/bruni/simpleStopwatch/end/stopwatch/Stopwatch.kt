package com.wing.tree.bruni.simpleStopwatch.end.stopwatch

import java.util.*
import kotlin.concurrent.timerTask

class Stopwatch(
    private val delay: Long,
    private val period: Long,
    private val callback: Callback
) {
    private var state: State = State.Stop
    private var timer: Timer? = null

    fun start() {
        if (state == State.Start) {
            return
        } else {
            state = State.Start

            timer?.clear()

            timer = Timer()

            timer?.scheduleAtFixedRate(
                timerTask {
                    callback.onTick(period)
                },
                delay,
                period
            )

            callback.onStart()
        }
    }

    fun stop() {
        if (state == State.Stop) {
            return
        } else {
            state = State.Stop

            timer?.clear()

            callback.onStop()
        }
    }

    fun reset() {
        if (state == State.Reset) {
            return
        } else {
            state = State.Reset

            timer?.clear()

            callback.onReset()
        }
    }

    private fun Timer.clear() {
        cancel()
        purge()
    }

    interface Callback {
        fun onStart()
        fun onStop()
        fun onReset()
        fun onTick(period: Long)
    }

    enum class State {
        Start,
        Stop,
        Reset
    }
}