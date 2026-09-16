package com.example.ui

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.KhmerPhrase
import com.example.data.model.SampleKhmerPhrases
import com.example.data.model.TtsItem
import com.example.data.util.KhmerNumberConverter
import com.example.engine.LanguageOption
import com.example.engine.TtsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class TtsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.ttsDao()
    val ttsManager = TtsManager(application)

    val historyList: StateFlow<List<TtsItem>> = dao.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoritesList: StateFlow<List<TtsItem>> = dao.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Input state
    private val _inputText = MutableStateFlow("សួស្តី! សូមស្វាគមន៍មកកាន់កម្មវិធីបំលែងអក្សរទៅជាសំឡេង។")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    // Number converter screen state
    private val _numberInput = MutableStateFlow("2025")
    val numberInput: StateFlow<String> = _numberInput.asStateFlow()

    private val _selectedPhraseCategory = MutableStateFlow("All")
    val selectedPhraseCategory: StateFlow<String> = _selectedPhraseCategory.asStateFlow()

    private val _phraseSearchQuery = MutableStateFlow("")
    val phraseSearchQuery: StateFlow<String> = _phraseSearchQuery.asStateFlow()

    private val _snackMessage = MutableStateFlow<String?>(null)
    val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

    // Audio export state
    private val _isExporting = MutableStateFlow(false)
    val isExporting: StateFlow<Boolean> = _isExporting.asStateFlow()

    fun updateInputText(newText: String) {
        _inputText.value = newText
    }

    fun clearInputText() {
        _inputText.value = ""
    }

    fun pasteFromClipboard() {
        val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = clipboard?.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0).text?.toString() ?: ""
            if (text.isNotBlank()) {
                _inputText.value = text
                showToast("បានបិទភ្ជាប់អត្ថបទ (Pasted from clipboard)")
            }
        }
    }

    fun copyToClipboard(text: String, label: String = "TTS Text") {
        val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard?.setPrimaryClip(clip)
        showToast("បានចម្លងទៅ Clipboard (Copied)")
    }

    fun speakCurrentText() {
        val text = _inputText.value.trim()
        if (text.isBlank()) {
            showToast("សូមបញ្ចូលអត្ថបទជាមុនសិន (Please enter text)")
            return
        }

        ttsManager.speak(
            text = text,
            onDone = {
                // Log to history after spoken
                saveToHistory(text)
            },
            onError = { err ->
                showToast("បញ្ហាសំឡេង: $err (TTS Error)")
            }
        )
    }

    fun speakPhrase(phrase: KhmerPhrase) {
        ttsManager.setLanguage("km")
        ttsManager.speak(
            text = phrase.khmerText,
            onDone = {
                saveToHistory(phrase.khmerText, category = phrase.category)
            }
        )
    }

    fun speakCustomText(text: String, lang: String = "km") {
        if (text.isBlank()) return
        ttsManager.setLanguage(lang)
        ttsManager.speak(
            text = text,
            onDone = {
                saveToHistory(text)
            }
        )
    }

    fun stopSpeaking() {
        ttsManager.stop()
    }

    fun setSpeechRate(rate: Float) {
        ttsManager.setSpeechRate(rate)
    }

    fun setSpeechPitch(pitch: Float) {
        ttsManager.setPitch(pitch)
    }

    fun setLanguage(code: String) {
        ttsManager.setLanguage(code)
    }

    fun resetVoiceSettings() {
        ttsManager.setSpeechRate(1.0f)
        ttsManager.setPitch(1.0f)
        showToast("កំណត់សំឡេងឡើងវិញ (Reset to 1.0x)")
    }

    private fun saveToHistory(text: String, category: String = "General") {
        viewModelScope.launch {
            val item = TtsItem(
                text = text,
                languageCode = ttsManager.currentLanguage.value,
                languageDisplayName = getLangDisplayName(ttsManager.currentLanguage.value),
                pitch = ttsManager.speechPitch.value,
                speed = ttsManager.speechRate.value,
                category = category
            )
            dao.insert(item)
        }
    }

    fun toggleFavorite(item: TtsItem) {
        viewModelScope.launch {
            dao.setFavorite(item.id, !item.isFavorite)
        }
    }

    fun deleteHistoryItem(item: TtsItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            dao.clearNonFavorites()
            showToast("បានសម្អាតប្រវត្តិ (History cleared)")
        }
    }

    // Number converter helpers
    fun updateNumberInput(input: String) {
        _numberInput.value = input.filter { it.isDigit() || it in "០១២៣៤៥៦៧៨៩" }
    }

    fun getConvertedKhmerNumberWords(): String {
        val raw = _numberInput.value
        if (raw.isBlank()) return "សូមបញ្ចូលលេខ (Enter a number)"
        val arabic = KhmerNumberConverter.toArabicDigits(raw)
        return try {
            val num = arabic.toLong()
            KhmerNumberConverter.numberToKhmerWords(num)
        } catch (e: Exception) {
            "លេខធំពេក (Number too large)"
        }
    }

    fun getConvertedKhmerDigits(): String {
        val raw = _numberInput.value
        if (raw.isBlank()) return ""
        val arabic = KhmerNumberConverter.toArabicDigits(raw)
        return KhmerNumberConverter.toKhmerDigits(arabic)
    }

    fun getConvertedRiel(): String {
        val raw = _numberInput.value
        if (raw.isBlank()) return ""
        val arabic = KhmerNumberConverter.toArabicDigits(raw)
        return try {
            val num = arabic.toLong()
            KhmerNumberConverter.readAsRiel(num)
        } catch (e: Exception) {
            ""
        }
    }

    fun getConvertedUsd(): String {
        val raw = _numberInput.value
        if (raw.isBlank()) return ""
        val arabic = KhmerNumberConverter.toArabicDigits(raw)
        return try {
            val num = arabic.toLong()
            KhmerNumberConverter.readAsUsd(num)
        } catch (e: Exception) {
            ""
        }
    }

    fun getConvertedYear(): String {
        val raw = _numberInput.value
        if (raw.isBlank()) return ""
        val arabic = KhmerNumberConverter.toArabicDigits(raw)
        return try {
            val num = arabic.toLong()
            KhmerNumberConverter.readAsYear(num)
        } catch (e: Exception) {
            ""
        }
    }

    fun getConvertedDigitByDigit(): String {
        val raw = _numberInput.value
        if (raw.isBlank()) return ""
        return KhmerNumberConverter.readDigitByDigit(raw)
    }

    fun speakConvertedNumber() {
        val words = getConvertedKhmerNumberWords()
        if (words.isNotBlank() && !words.startsWith("សូម") && !words.startsWith("លេខ")) {
            speakCustomText(words, "km")
        }
    }

    fun speakConvertedRiel() {
        val riel = getConvertedRiel()
        if (riel.isNotBlank()) {
            speakCustomText(riel, "km")
        }
    }

    fun speakConvertedUsd() {
        val usd = getConvertedUsd()
        if (usd.isNotBlank()) {
            speakCustomText(usd, "km")
        }
    }

    fun speakConvertedYear() {
        val year = getConvertedYear()
        if (year.isNotBlank()) {
            speakCustomText(year, "km")
        }
    }

    fun speakConvertedDigitByDigit() {
        val digits = getConvertedDigitByDigit()
        if (digits.isNotBlank()) {
            speakCustomText(digits, "km")
        }
    }

    fun setPhraseCategory(category: String) {
        _selectedPhraseCategory.value = category
    }

    fun setPhraseSearch(query: String) {
        _phraseSearchQuery.value = query
    }

    fun exportAudio(context: Context, text: String) {
        if (text.isBlank()) {
            showToast("សូមបញ្ចូលអត្ថបទ (Text is empty)")
            return
        }
        _isExporting.value = true
        val cacheDir = context.cacheDir
        val audioFile = File(cacheDir, "somleng_${System.currentTimeMillis()}.wav")

        ttsManager.synthesizeToFile(
            text = text,
            outFile = audioFile,
            onDone = { file ->
                _isExporting.value = false
                shareAudioFile(context, file)
            },
            onError = { err ->
                _isExporting.value = false
                showToast("មិនអាចបង្កើតឯកសារសំឡេង: $err (Export failed)")
            }
        )
    }

    private fun shareAudioFile(context: Context, file: File) {
        try {
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "audio/wav"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(Intent.createChooser(shareIntent, "ចែករំលែកសំឡេង / Share Audio"))
        } catch (e: Exception) {
            // Direct share fallback
            showToast("បានរក្សាទុកឯកសារសំឡេង: ${file.name}")
        }
    }

    private fun getLangDisplayName(code: String): String {
        return when (code) {
            "km" -> "ភាសាខ្មែរ (Khmer)"
            "en" -> "English (US)"
            "en-GB" -> "English (UK)"
            "th" -> "ภาษาไทย (Thai)"
            "vi" -> "Tiếng Việt (Vietnamese)"
            "zh" -> "中文 (Chinese)"
            "fr" -> "Français (French)"
            "ja" -> "日本語 (Japanese)"
            "ko" -> "한국어 (Korean)"
            else -> code
        }
    }

    fun showToast(msg: String) {
        _snackMessage.value = msg
        Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show()
    }

    fun clearSnackMessage() {
        _snackMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
