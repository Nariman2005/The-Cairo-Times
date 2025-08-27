package com.cocochanel.trial.ui.features.loginpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginPageViewModel : ViewModel() {
    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onUsernameChange(newUsername: String) {
        username = newUsername
        // Clear error when user starts typing
        if (errorMessage != null) {
            errorMessage = null
        }
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        // Clear error when user starts typing
        if (errorMessage != null) {
            errorMessage = null
        }
    }

    fun onLoginClick(): Boolean {
        return when {
            username.isBlank() || password.isBlank() -> {
                errorMessage = "Please enter username and password."
                false
            }
            username == "nariman" && password == "123" -> {
                errorMessage = null
                true // Login successful
            }
            else -> {
                errorMessage = "Incorrect username or password."
                false
            }
        }
    }

    fun clearError() {
        errorMessage = null
    }
}
