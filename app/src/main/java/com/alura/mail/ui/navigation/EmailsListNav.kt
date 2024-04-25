package com.alura.mail.ui.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alura.mail.model.Email
import com.alura.mail.ui.home.EmailsListScreen

internal const val emailListRoute = "emailList"

fun NavGraphBuilder.emailsListScreen(
    onOpenEmail: (Email) -> Unit = {},
    listState: LazyListState
) {
    composable(emailListRoute) {
        EmailsListScreen(
            listState = listState,
            onClick = {
                onOpenEmail(it)
            }
        )
    }
}