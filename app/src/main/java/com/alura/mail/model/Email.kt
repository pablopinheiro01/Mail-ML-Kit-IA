package com.alura.mail.model

data class Email(
    val id: String,
    val subject: String,
    val content: String,
    val time: Long,
    val color: Long,
    val user: User,
)