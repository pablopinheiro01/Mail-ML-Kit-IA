package com.alura.mail.model

data class LanguageModel(
    val id: String,
    val name: String,
    val downloadState: DownloadState,
    val size: String = ""
)

enum class DownloadState {
    DOWNLOADED,
    DOWNLOADING,
    NOT_DOWNLOADED,
}