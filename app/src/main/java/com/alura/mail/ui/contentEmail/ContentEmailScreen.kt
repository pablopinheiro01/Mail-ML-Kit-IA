package com.alura.mail.ui.contentEmail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alura.mail.R
import com.alura.mail.extensions.toFormattedDate
import com.alura.mail.model.Email
import com.alura.mail.model.Suggestion
import com.alura.mail.model.SuggestionAction
import com.alura.mail.ui.components.LoadScreen
import com.alura.mail.ui.settings.LanguageDialog
import com.alura.mail.util.ActionHandler

@Composable
fun ContentEmailScreen(
    onExecuteAction: (Suggestion) -> Unit = {},
) {
    val viewModel = hiltViewModel<ContentEmailViewModel>()
    val state by viewModel.uiState.collectAsState()

    state.selectedSuggestion?.let { suggestion ->
        onExecuteAction(suggestion)
    }

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
            EmailContent(email, state.rangeList)

            RepliesContainer(email.replies)

            SmartSuggestionsContainer(viewModel)

            ReplyContainer(viewModel)

            Spacer(modifier = Modifier.height(16.dp))
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
private fun ReplyContainer(viewModel: ContentEmailViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        var showEditTextField by remember { mutableStateOf(false) }

        if (showEditTextField) {
            var replyState by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = replyState,
                onValueChange = { replyState = it },
                label = {
                    Text(text = stringResource(R.string.send_reply))
                },
                trailingIcon = {
                    IconButton(onClick = {
                        showEditTextField = false
                        viewModel.addReply(replyState)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = stringResource(R.string.add_reply),
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        } else {
            OutlinedButton(
                onClick = { showEditTextField = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.reply),
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}

@Composable
private fun RepliesContainer(replies: List<Email>) {
    replies.forEach { reply ->
        ExpandableEmailItem(reply)
    }
}

@Composable
private fun SmartSuggestionsContainer(
    viewModel: ContentEmailViewModel
) {
    val state by viewModel.uiState.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp)
        ) {
            state.suggestions.forEach { suggestion ->
                SuggestionChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = {
                        if (suggestion.action == SuggestionAction.SMART_REPLY) {
                            viewModel.addReply(suggestion.text)
                        } else {
                            viewModel.setSelectSuggestion(suggestion)
                        }
                    },
                    label = {
                        Text(
                            suggestion.text,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 100.dp)
                        )
                    },
                    shape = CircleShape,
                    icon = {
                        suggestion.icon?.let {
                            Icon(
                                painter = painterResource(id = suggestion.icon),
                                contentDescription = suggestion.text,
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                )
            }
        }
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
                    if (email.subject.isNotEmpty()) {
                        Text(
                            text = email.user.name.first().toString(),
                            color = Color.White,
                            fontSize = 22.sp,
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.profile_pic),
                            contentDescription = "Usuário"
                        )
                    }
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
fun EmailContent(email: Email, rangeList: List<IntRange> = emptyList()) {

    if (email.subject.isNotEmpty()) {
        Text(
            text = email.subject,
            modifier = Modifier.padding(16.dp),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
        )
    }

    if (rangeList.isNotEmpty()) {
        MountHiText(email.content, rangeList)
    } else {
        Text(
            text = email.content,
            modifier = Modifier.padding(16.dp),
            style = TextStyle(fontSize = 18.sp)
        )
    }
}

@Composable
fun MountHiText(content: String, rangeList: List<IntRange> = emptyList()) {

    val context = LocalContext.current
    var textLabel = AnnotatedString("")
    var currentRange = 0

    rangeList.forEachIndexed { index, it ->
        val normalText = content.substring(currentRange, it.first)
        val linkText = content.substring(it.first, it.last)
        val tempText = buildAnnotatedString {
            append(normalText)
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline
                )
            ) { append(linkText) }

            addStringAnnotation(
                tag = index.toString(),
                annotation = linkText,
                start = it.first,
                end = it.last
            )
        }
        textLabel = textLabel.plus(tempText)
        currentRange = it.last
    }

    SelectionContainer {
        ClickableText(
            modifier = Modifier.padding(16.dp),
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            text = textLabel,
            onClick = { offset ->
                val tagIndex = textLabel.spanStyles.indexOfFirst { offset in it.start until it.end }

                if (tagIndex > -1) {
                    val entity = textLabel.getStringAnnotations(
                        start = 0, end = Int.MAX_VALUE
                    )[tagIndex]

                    val actionHandler = ActionHandler()
                    actionHandler.copyToTransferArea(context, entity.item)
                }
            }
        )
    }
}


@Composable
private fun ExpandableEmailItem(email: Email) {
    var isExpanded by remember { mutableStateOf(email.replies.isEmpty()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
    ) {
        EmailHeader(email = email)
        if (isExpanded) {
            EmailContent(email)
        }
    }
}