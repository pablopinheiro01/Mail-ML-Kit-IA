package com.alura.mail.ui.contentEmail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.mail.mlkit.EntityExtractor
import com.alura.mail.mlkit.ResponseGenerator
import com.alura.mail.mlkit.TextTranslator
import com.alura.mail.model.Language
import com.alura.mail.model.Message
import com.alura.mail.model.Suggestion
import com.alura.mail.model.SuggestionAction
import com.alura.mail.samples.EmailDao
import com.alura.mail.ui.navigation.emailIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ContentEmailViewModel @Inject constructor(
    private val textTranslator: TextTranslator,
    private val responseGenerator: ResponseGenerator,
    private val entityExtraction: EntityExtractor,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val emailDao = EmailDao()

    private val emailId: String = checkNotNull(savedStateHandle[emailIdArgument])

    private val _uiState = MutableStateFlow(ContentEmailUiState())
    var uiState = _uiState.asStateFlow()

    init {
        loadEmail()
    }

    private fun loadSmartActions() {
    }

    private fun loadSmartSuggestions() {
        _uiState.value.selectedEmail?.let { email ->
            val conversation = mutableListOf(
                Message(
                    content = email.content,
                    isLocalUser = false
                )
            )

            email.replies.forEach { reply ->
                conversation.add(
                    Message(
                        content = reply.content,
                        isLocalUser = email.user.name != reply.user.name
                    )
                )
            }

        }
    }

    private fun loadEmail() {
        viewModelScope.launch {
            val email = emailDao.getEmailById(emailId)
            _uiState.value = _uiState.value.copy(
                selectedEmail = email,
                originalContent = email?.content,
                originalSubject = email?.subject
            )
            loadSmartActions()
            loadSmartSuggestions()

            identifyEmailLanguage()
            identifyLocalLanguage()
        }
    }

    private fun identifyEmailLanguage() {
        _uiState.value.selectedEmail?.let { email ->
            textTranslator.languageIdentifier(
                text = email.content,
                onSuccess = { language ->
                    _uiState.value = _uiState.value.copy(
                        languageIdentified = language
                    )
                    verifyIfNeedTranslate()
                },
                onFailure = {

                }
            )
        }
    }

    private fun identifyLocalLanguage() {
        val languageCode = Locale.getDefault().language
        val languageName = Locale.getDefault().displayLanguage

        _uiState.value = _uiState.value.copy(
            localLanguage = Language(
                code = languageCode,
                name = languageName
            )
        )
        verifyIfNeedTranslate()
        downloadDefaultLanguageModel()
    }

    private fun downloadDefaultLanguageModel() {

        val defaultLanguage = _uiState.value.localLanguage?.code.toString()

        textTranslator.downloadModel(
            modelName = defaultLanguage,
            onSuccess = {
                translateEmail()

                Log.i("defaultLanguage", "modelo default baixado com ssucesso ")
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    translatedState = TranslatedState.NOT_TRANSLATED
                )
                Log.i("defaultLanguage", "error nao baixou a brincadeira ")
            }
        )

    }

    private fun verifyIfNeedTranslate() {
        val languageIdentified = _uiState.value.languageIdentified
        val localLanguage = _uiState.value.localLanguage

        if (languageIdentified != null && localLanguage != null) {
            if (languageIdentified.code != localLanguage.code) {
                _uiState.value = _uiState.value.copy(
                    canBeTranslate = true
                )
            }
        }
    }

    fun tryTranslateEmail() {
        with(_uiState.value) {
            if (translatedState == TranslatedState.TRANSLATED) {
                selectedEmail?.let { email ->
                    _uiState.value = _uiState.value.copy(
                        selectedEmail = email.copy(
                            content = originalContent.toString(),
                            subject = originalSubject.toString()
                        ),
                        translatedState = TranslatedState.NOT_TRANSLATED
                    )
                }
            } else {
                val languageIdentified = _uiState.value.languageIdentified?.code.toString()

                textTranslator.verifyDownloadModel(
                    modelCode = languageIdentified,
                    onSuccess = {
                        translateEmail()
                    },
                    onFailure = {
                        _uiState.value = _uiState.value.copy(
                            showDownloadLanguageDialog = true
                        )
                    }
                )

                setTranslateState(TranslatedState.TRANSLATING)
            }
        }
    }

    fun setTranslateState(state: TranslatedState) {
        _uiState.value = _uiState.value.copy(
            translatedState = state
        )
    }

    private fun translateEmail() {
        with(_uiState.value) {
            selectedEmail?.let { email ->
                languageIdentified?.let { languageIdentified ->
                    localLanguage?.let { localLanguage ->
                        textTranslator.textTranslate(
                            text = email.content,
                            sourceLanguage = languageIdentified.code,
                            targetLanguage = localLanguage.code,
                            onSuccess = {
                                _uiState.value = _uiState.value.copy(
                                    selectedEmail = email.copy(
                                        content =  it
                                    ),
                                    translatedState = TranslatedState.TRANSLATED
                                )

                                translateSubject()
                            },
                            onFailure = {
                                _uiState.value = _uiState.value.copy(
                                    translatedState = TranslatedState.NOT_TRANSLATED
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    private fun translateSubject() {
        with(_uiState.value) {
            selectedEmail?.let { email ->
                languageIdentified?.let { languageIdentified ->
                    localLanguage?.let { localLanguage ->
                        textTranslator.textTranslate(
                            text = email.subject,
                            sourceLanguage = languageIdentified.code,
                            targetLanguage = localLanguage.code,
                            onSuccess = {
                                _uiState.value = _uiState.value.copy(
                                    selectedEmail = email.copy(
                                        subject =  it
                                    ),
                                )
                            },
                            onFailure = {
                                _uiState.value = _uiState.value.copy(
                                    translatedState = TranslatedState.NOT_TRANSLATED
                                )
                            }
                        )

                    }
                }
            }
        }
    }

    fun downloadLanguageModel() {
        val languageIdentified = _uiState.value.languageIdentified?.code ?: return

        textTranslator.downloadModel(
            modelName = languageIdentified,
            onSuccess = {
                translateEmail()

                Log.i("defaultLanguage", "modelo default baixado com ssucesso ")
            },
            onFailure = {
                _uiState.value = _uiState.value.copy(
                    translatedState = TranslatedState.NOT_TRANSLATED
                )
                Log.i("defaultLanguage", "error nao baixou a brincadeira ")
            }
        )
    }

    fun showDownloadLanguageDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(
            showDownloadLanguageDialog = show
        )
    }

    fun hideTranslateButton() {
        _uiState.value = _uiState.value.copy(
            showTranslateButton = false
        )
    }

    fun setSelectSuggestion(suggestion: Suggestion) {
        _uiState.value = _uiState.value.copy(
            selectedSuggestion = suggestion
        )
    }

    fun addReply(text: String) {
        _uiState.value.selectedEmail?.let { email ->
            _uiState.value = _uiState.value.copy(
                selectedEmail = email.copy(
                    replies = email.replies + EmailDao().mountLocalEmail(text)
                )
            )
        }

        if (_uiState.value.suggestions.first().action == SuggestionAction.SMART_REPLY) {
            loadSmartSuggestions()
        }
    }
}