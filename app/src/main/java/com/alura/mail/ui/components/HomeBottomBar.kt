package com.alura.mail.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.alura.mail.R
import com.alura.mail.ui.navigation.emailListRoute
import com.alura.mail.ui.navigation.translateSettingsRoute


@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    currentTab: String,
    onItemSelected: (String) -> Unit,
) {
    Box(modifier = modifier) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
        ) {
            screenItems.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            if (currentTab == item.route) item.resourceId.first else item.resourceId.second,
                            contentDescription = stringResource(item.title),
                        )
                    },
                    label = { Text(stringResource(item.title)) },
                    selected = currentTab == item.route,
                    onClick = { onItemSelected(item.route) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
            }
        }
    }
}

private val screenItems = listOf(
    BottomScreenItem(
        title = R.string.bottom_title_home,
        route = emailListRoute,
        resourceId = Pair(
            Icons.Filled.Home,
            Icons.Outlined.Home,
        ),
    ),
    BottomScreenItem(
        title = R.string.bottom_title_settings,
        route = translateSettingsRoute,
        resourceId = Pair(
            Icons.Filled.Settings,
            Icons.Outlined.Settings,
        ),
    ),
)

private data class BottomScreenItem(
    val title: Int,
    val route: String,
    val resourceId: Pair<ImageVector, ImageVector>,
)