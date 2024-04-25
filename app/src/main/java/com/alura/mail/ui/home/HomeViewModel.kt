package com.alura.mail.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.mail.R
import com.alura.mail.model.Email
import com.alura.mail.samples.EmailDao
import com.alura.mail.ui.navigation.contentEmailFullPath
import com.alura.mail.ui.navigation.emailListRoute
import com.alura.mail.ui.navigation.translateSettingsRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(emptyList()))
    var uiState = _uiState.asStateFlow()

    init {
        loadEmails()
    }

    private fun loadEmails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(emails = EmailDao().getEmails())
        }
    }

    fun changeCurrentDestination(route: String) {
        _uiState.value = _uiState.value.copy(
            showEmailsList = route == emailListRoute,
            currentDestination = route
        )
    }

    fun setSelectedEmail(email: Email) {
        _uiState.value = _uiState.value.copy(
            selectedEmail = email
        )
    }

    fun getAppBarTitle(): Int {
        return when (uiState.value.currentDestination) {
            translateSettingsRoute -> R.string.language_settings
            contentEmailFullPath -> R.string.back
            else -> R.string.default_appbar_title
        }
    }
}