package com.alura.mail.model

data class EntityInfo(
    val entityText: String,
    val action: SuggestionAction = SuggestionAction.SMART_REPLY,
)
