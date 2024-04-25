package com.alura.mail.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.alura.mail.R
import com.alura.mail.model.DownloadState
import com.alura.mail.model.LanguageModel
import com.alura.mail.ui.components.LoadScreen

@Composable
fun TranslateSettingsScreen(modifier: Modifier = Modifier) {
    val translateSettingsViewModel = hiltViewModel<TranslateSettingsViewModel>()
    val state by translateSettingsViewModel.uiState.collectAsState()

    when (state.loadModelsState) {
        AppState.Loaded -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .fillMaxSize()
            ) {
                LazyColumn {
                    if (state.downloadedLanguageModels.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.download_warning_languages),
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                modifier = modifier.padding(16.dp),
                            )
                        }

                        item {
                            SeparatorLanguageItem(stringResource(R.string.download_completed))
                            state.downloadedLanguageModels.forEach { language ->
                                LanguageItem(
                                    languageModel = language,
                                    onClick = {
                                        translateSettingsViewModel.selectedLanguage(language)
                                        translateSettingsViewModel.showDeleteDialog(
                                            true
                                        )
                                    }
                                )
                            }
                        }
                    }

                    if (state.notDownloadedLanguageModels.isNotEmpty()) {
                        item {
                            SeparatorLanguageItem(stringResource(R.string.tap_to_download))
                            state.notDownloadedLanguageModels.forEach { language ->
                                LanguageItem(
                                    languageModel = language,
                                    onClick = {
                                        translateSettingsViewModel.selectedLanguage(language)
                                        translateSettingsViewModel.showDownloadDialog(true)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (state.showDownloadLanguageDialog && state.selectedLanguageModel != null) {
                state.selectedLanguageModel?.let { selectedLanguage ->
                    LanguageDialog(
                        title = selectedLanguage.name,
                        description = stringResource(R.string.download_warning_emails),
                        confirmText = stringResource(R.string.baixar),
                        onDismiss = {
                            translateSettingsViewModel.showDownloadDialog(false)
                        },
                        onConfirm = {
                            translateSettingsViewModel.downloadLanguage(selectedLanguage)
                            translateSettingsViewModel.showDownloadDialog(false)
                        }
                    )
                }
            }


            if (state.showDeleteLanguageDialog && state.selectedLanguageModel != null) {
                state.selectedLanguageModel?.let { selectedLanguage ->
                    LanguageDialog(
                        title = "${selectedLanguage.name} (${selectedLanguage.size})",
                        description = stringResource(R.string.delete_warning),
                        confirmText = stringResource(R.string.remover),
                        onDismiss = {
                            translateSettingsViewModel.showDeleteDialog(false)
                        },
                        onConfirm = {
                            translateSettingsViewModel.removeLanguage(selectedLanguage)
                            translateSettingsViewModel.showDeleteDialog(false)
                        }
                    )
                }
            }
        }

        AppState.Loading -> {
            LoadScreen()
        }

        AppState.Error -> {
            EmptyScreen()
        }
    }

    SetupDisposableEffect(translateSettingsViewModel)

}

@Composable
private fun SetupDisposableEffect(translateSettingsViewModel: TranslateSettingsViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                translateSettingsViewModel.loadLanguages()
            } else if (event == Lifecycle.Event.ON_STOP) {
                translateSettingsViewModel.cleanState()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
private fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_languages_available),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SeparatorLanguageItem(title: String) {
    Text(
        text = title,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 4.dp),
    )
}

@Composable
private fun LanguageItem(
    languageModel: LanguageModel,
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = languageModel.name,
            fontSize = 18.sp,
        )

        Icon(
            painter = painterResource(id = getIconByLanguage(languageModel)),
            contentDescription = getDescriptionByLanguage(languageModel),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            modifier = Modifier.size(26.dp),
        )
    }
}

@Composable
fun LanguageDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    description: String,
    confirmText: String,
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        MaterialTheme.shapes.extraLarge
                    )
                    .padding(22.dp)
            ) {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    text = description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.size(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = confirmText,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onConfirm() }
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }

    }
}

@Composable
private fun getIconByLanguage(languageModel: LanguageModel) = when (languageModel.downloadState) {
    DownloadState.DOWNLOADED -> R.drawable.ic_delete
    DownloadState.DOWNLOADING -> R.drawable.ic_downloading
    DownloadState.NOT_DOWNLOADED -> R.drawable.ic_download
}

@Composable
private fun getDescriptionByLanguage(languageModel: LanguageModel) =
    when (languageModel.downloadState) {
        DownloadState.DOWNLOADED -> stringResource(R.string.downloaded_language)
        DownloadState.DOWNLOADING -> stringResource(R.string.downloading_language)
        DownloadState.NOT_DOWNLOADED -> stringResource(R.string.tap_to_download)
    }