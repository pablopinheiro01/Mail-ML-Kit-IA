package com.alura.mail.mlkit

import com.alura.mail.model.Suggestion

class ResponseGenerator {

    fun messageToSuggestionAction(messages: List<String>): List<Suggestion> {
        return messages.map { message ->
            Suggestion(message)
        }
    }
}
