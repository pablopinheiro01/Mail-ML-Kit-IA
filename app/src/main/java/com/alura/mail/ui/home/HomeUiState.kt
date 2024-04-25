package com.alura.mail.ui.home

import com.alura.mail.model.Email

data class HomeUiState(
    val emails: List<Email>,
    val showEmailsList: Boolean = true,
    val selectedEmail: Email? = null,
    val currentDestination: String = ""
)