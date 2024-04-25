package com.alura.mail.ui.contentEmail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alura.mail.mlkit.TextTranslator
import com.alura.mail.model.Language
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
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val emailDao = EmailDao()

    private val emailId: String = checkNotNull(savedStateHandle[emailIdArgument])

    private val _uiState = MutableStateFlow(ContentEmailUiState())
    var uiState = _uiState.asStateFlow()

    init {
        loadEmail()
    }

    private fun loadEmail() {
        viewModelScope.launch {
            val email = emailDao.getEmailById(emailId)
            _uiState.value = _uiState.value.copy(
                selectedEmail = email,
                originalContent = email?.content,
                originalSubject = email?.subject
            )
            identifyEmailLanguage()
            identifyLocalLanguage()
        }
    }

    private fun identifyEmailLanguage() {
        _uiState.value.selectedEmail?.let { email ->
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
                    }
                }
            }
        }
    }

    fun downloadLanguageModel() {
        val languageIdentified = _uiState.value.languageIdentified?.code ?: return
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
}