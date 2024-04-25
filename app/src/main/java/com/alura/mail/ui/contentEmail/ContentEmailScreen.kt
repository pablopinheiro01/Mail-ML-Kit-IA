package com.alura.mail.ui.contentEmail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alura.mail.R
import com.alura.mail.extensions.toFormattedDate
import com.alura.mail.model.Email
import com.alura.mail.ui.components.LoadScreen
import com.alura.mail.ui.settings.LanguageDialog

@Composable
fun ContentEmailScreen() {
    val viewModel = hiltViewModel<ContentEmailViewModel>()
    val state by viewModel.uiState.collectAsState()

    state.selectedEmail?.let { email ->
        val textTranslateFor = stringResource(
            R.string.translate_from_to,
            state.languageIdentified?.name.toString(),
            state.localLanguage?.name.toString()
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            EmailHeader(email)
            if (state.canBeTranslate) {
                AnimatedVisibility(
                    visible = state.showTranslateButton,
                ) {
                    EmailSubHeader(
                        textTranslateFor = textTranslateFor,
                        translatedState = state.translatedState,
                        onTranslate = {
                            viewModel.tryTranslateEmail()
                        },
                        onClose = {
                            viewModel.hideTranslateButton()
                        },
                    )
                }
            }
            EmailContent(email)
        }

        if (state.showDownloadLanguageDialog) {
            LanguageDialog(
                title = "${state.languageIdentified?.name}",
                description = stringResource(R.string.download_warning_emails),
                confirmText = stringResource(R.string.baixar),
                onConfirm = {
                    viewModel.downloadLanguageModel()
                    viewModel.showDownloadLanguageDialog(false)
                },
                onDismiss = {
                    viewModel.setTranslateState(TranslatedState.NOT_TRANSLATED)
                    viewModel.showDownloadLanguageDialog(false)
                },
            )
        }
    } ?: run {
        LoadScreen()
    }
}

@Composable
private fun EmailSubHeader(
    textTranslateFor: String,
    translatedState: TranslatedState,
    onTranslate: () -> Unit = {},
    onClose: () -> Unit = {},
) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onTranslate() }
                    .padding(16.dp)
                    .weight(0.8f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_translate),
                    contentDescription = "Traduzir",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.size(16.dp))

                Column {
                    Text(
                        text = textTranslateFor,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = if (translatedState == TranslatedState.TRANSLATED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseOnSurface,
                        fontWeight = if (translatedState == TranslatedState.TRANSLATED) FontWeight.Normal else FontWeight.Bold
                    )
                    if (translatedState == TranslatedState.TRANSLATED) {
                        Text(
                            text = stringResource(R.string.show_original),
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            IconButton(
                modifier = Modifier.weight(0.1f),
                onClick = { onClose() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Esconder sugestão de tradução",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        if (translatedState == TranslatedState.TRANSLATING) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun EmailHeader(email: Email) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(email.color)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = email.user.name.first().toString(),
                        color = Color.White,
                        fontSize = 22.sp,
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = email.user.name,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = email.time.toFormattedDate(),
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    )
                }
            }

            Icon(
                Icons.Default.MoreVert, "Mais informações",
            )
        }
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun EmailContent(email: Email) {
    Text(
        text = email.subject,
        modifier = Modifier.padding(16.dp),
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
    )

    Text(
        text = email.content,
        modifier = Modifier.padding(16.dp),
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
    )
}