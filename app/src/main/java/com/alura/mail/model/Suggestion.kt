package com.alura.mail.model

data class Suggestion(
    val text: String,
    val action: SuggestionAction = SuggestionAction.SMART_REPLY,
    val icon: Int? = null
)

enum class SuggestionAction {
    SMART_REPLY, // 0
    ADDRESS, // 1
    DATE_TIME, // 2
    EMAIL, // 3
    FLIGHT_NUMBER, // 4
    IBAN, // 5
    ISBN, // 6
    PAYMENT_CARD_NUMBER, // 7
    PHONE_NUMBER,  // 8
    TRACKING_NUMBER, // 9
    URL, // 10
    MONEY, // 11
}
