package com.alura.mail.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alura.mail.R

@Composable
fun HomeFAB(expanded: Boolean) {
    ExtendedFloatingActionButton(
        onClick = { /*TODO*/ },
        text = {
            Text(
                text = stringResource(R.string.write),
                color = MaterialTheme.colorScheme.primary
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = "adicionar",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
        expanded = expanded
    )
}