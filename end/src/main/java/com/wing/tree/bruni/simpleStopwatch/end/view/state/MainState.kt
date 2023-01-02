package com.wing.tree.bruni.simpleStopwatch.end.view.state

sealed class MainState {
    abstract val isPlayEnabled: Boolean
    abstract val isPauseEnabled: Boolean
    abstract val isStopEnabled: Boolean

    object Play: MainState() {
        override val isPlayEnabled: Boolean = false
        override val isPauseEnabled: Boolean = true
        override val isStopEnabled: Boolean = true
    }

    object Pause: MainState() {
        override val isPlayEnabled: Boolean = true
        override val isPauseEnabled: Boolean = false
        override val isStopEnabled: Boolean = true
    }

    object Stop: MainState() {
        override val isPlayEnabled: Boolean = true
        override val isPauseEnabled: Boolean = false
        override val isStopEnabled: Boolean = false
    }
}
