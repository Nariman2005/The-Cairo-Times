package com.cocochanel.trial.ui.features.landingpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LandingPageViewModel : ViewModel() {
    var startAnimation by mutableStateOf(false)
        private set

    var showImage by mutableStateOf(false)
        private set

    var showLogin by mutableStateOf(false)
        private set

    init {
        startAnimationSequence()
    }

    private fun startAnimationSequence() {
        viewModelScope.launch {
            delay(2000)
            startAnimation = true
            delay(300)
            showImage = true
            delay(500)
            showLogin = true
        }
    }
}
