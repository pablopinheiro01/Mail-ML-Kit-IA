package com.alura.mail.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alura.mail.ui.contentEmail.ContentEmailScreen

internal const val contentEmailRoute = "emails"
internal const val emailIdArgument = "emailId"
internal const val contentEmailFullPath = "$contentEmailRoute/{$emailIdArgument}"

fun NavGraphBuilder.contentEmailScreen() {
    composable(contentEmailFullPath) {
        ContentEmailScreen()
    }
}

internal fun NavHostController.navigateToContentEmailScreen(
    emailId: String
) {
    navigateDirect("$contentEmailRoute/$emailId")
}