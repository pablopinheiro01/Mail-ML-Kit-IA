package com.alura.mail.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alura.mail.ui.contentEmail.ContentEmailScreen
import com.alura.mail.util.ActionHandler

internal const val contentEmailRoute = "emails"
internal const val emailIdArgument = "emailId"
internal const val contentEmailFullPath = "$contentEmailRoute/{$emailIdArgument}"

fun NavGraphBuilder.contentEmailScreen() {
    composable(contentEmailFullPath) {
        val context = LocalContext.current
        val actionHandler = ActionHandler()
        ContentEmailScreen {
            actionHandler.executeAction(context, it)
        }
    }
}

internal fun NavHostController.navigateToContentEmailScreen(
    emailId: String
) {
    navigateDirect("$contentEmailRoute/$emailId")
}