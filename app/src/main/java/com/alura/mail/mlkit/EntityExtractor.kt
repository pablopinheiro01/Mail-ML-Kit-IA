package com.alura.mail.mlkit

import com.alura.mail.R
import com.alura.mail.model.EntityInfo
import com.alura.mail.model.Suggestion
import com.alura.mail.model.SuggestionAction

class EntityExtractor {

    fun entityToSuggestionAction(entities: List<EntityInfo>): List<Suggestion> {
        return entities.map { entity ->
            val suggestion = Suggestion(text = entity.entityText, action = entity.action)
            when (entity.action) {
                SuggestionAction.DATE_TIME -> suggestion.copy(icon = R.drawable.ic_date_time)
                SuggestionAction.PHONE_NUMBER -> suggestion.copy(icon = R.drawable.ic_call)
                SuggestionAction.ADDRESS -> suggestion.copy(icon = R.drawable.ic_location)
                SuggestionAction.EMAIL -> suggestion.copy(icon = R.drawable.ic_email)
                SuggestionAction.URL -> suggestion.copy(icon = R.drawable.ic_link)
                else -> suggestion.copy(icon = R.drawable.ic_copy)
            }
        }
    }

    private fun getSuggestionActionByEntity(entityType: Int): SuggestionAction {
        return when (entityType) {
            1 -> SuggestionAction.ADDRESS
            2 -> SuggestionAction.DATE_TIME
            3 -> SuggestionAction.EMAIL
            4 -> SuggestionAction.FLIGHT_NUMBER
            5 -> SuggestionAction.IBAN
            6 -> SuggestionAction.ISBN
            7 -> SuggestionAction.PAYMENT_CARD_NUMBER
            8 -> SuggestionAction.PHONE_NUMBER
            9 -> SuggestionAction.TRACKING_NUMBER
            10 -> SuggestionAction.URL
            11 -> SuggestionAction.MONEY
            else -> SuggestionAction.SMART_REPLY
        }
    }
}