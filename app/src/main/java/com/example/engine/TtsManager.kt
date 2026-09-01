package com.example.engine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.Locale

data class LanguageOption(
    val code: String,
    val locale: Locale,
    val displayName: String,
    val isKhmer: Boolean = false,
    val isSupported: Boolean = true
)

class TtsManager(private val context: Context) : TextToSpeech.OnInitListener {

    companion object {
        private const val TAG = "TtsManager"
        val KHMER_LOCALE = Locale("km", "KH")
    }

    private var tts: TextToSpeech? = null

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentLanguage = MutableStateFlow("km")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _speechRate = MutableStateFlow(1.0f)
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    private val _speechPitch = MutableStateFlow(1.0f)
    val speechPitch: StateFlow<Float> = _speechPitch.asStateFlow()

    private val _engineName = MutableStateFlow("Default TTS")
    val engineName: StateFlow<String> = _engineName.asStateFlow()

    private val _availableLanguages = MutableStateFlow<List<LanguageOption>>(emptyList())
    val availableLanguages: StateFlow<List<LanguageOption>> = _availableLanguages.asStateFlow()

    private val _khmerSupportStatus = MutableStateFlow("Checking...")
    val khmerSupportStatus: StateFlow<String> = _khmerSupportStatus.asStateFlow()

    private var onDoneCallback: (() -> Unit)? = null
    private var onErrorCallback: ((String) -> Unit)? = null

    init {
        initTts()
    }

    fun initTts() {
        tts?.stop()
        tts?.shutdown()
        _isReady.value = false
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val engine = tts?.defaultEngine ?: "Android TTS"
            _engineName.value = engine
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    onDoneCallback?.invoke()
                }

                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    onErrorCallback?.invoke("Playback error")
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?, errorCode: Int) {
                    _isSpeaking.value = false
                    onErrorCallback?.invoke("Playback error ($errorCode)")
                }
            })

            checkLanguages()
            setLanguage("km")
            _isReady.value = true
            Log.d(TAG, "TTS initialized successfully with engine: $engine")
        } else {
            _isReady.value = false
            _khmerSupportStatus.value = "Initialization failed ($status)"
            Log.e(TAG, "TTS Initialization failed: $status")
        }
    }

    private fun checkLanguages() {
        val t = tts ?: return
        val list = mutableListOf<LanguageOption>()

        // Khmer
        val kmResult = t.isLanguageAvailable(KHMER_LOCALE)
        val kmSupported = kmResult >= TextToSpeech.LANG_AVAILABLE
        _khmerSupportStatus.value = when (kmResult) {
            TextToSpeech.LANG_AVAILABLE, TextToSpeech.LANG_COUNTRY_AVAILABLE, TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE ->
                "គាំទ្រភាសាខ្មែរ (Khmer Voice Ready)"
            TextToSpeech.LANG_MISSING_DATA ->
                "ត្រូវការទាញយកទិន្នន័យសំឡេង (Voice Data Needed)"
            else ->
                "ម៉ាស៊ីន TTS បច្ចុប្បន្នអាចនឹងមិនគាំទ្រខ្មែរពេញលេញ (Khmer may use fallback)"
        }

        list.add(
            LanguageOption(
                code = "km",
                locale = KHMER_LOCALE,
                displayName = "ភាសាខ្មែរ (Khmer)",
                isKhmer = true,
                isSupported = kmSupported
            )
        )

        // Popular languages
        val popular = listOf(
            LanguageOption("en", Locale.US, "English (US)"),
            LanguageOption("en-GB", Locale.UK, "English (UK)"),
            LanguageOption("th", Locale("th", "TH"), "ภาษาไทย (Thai)"),
            LanguageOption("vi", Locale("vi", "VN"), "Tiếng Việt (Vietnamese)"),
            LanguageOption("zh", Locale.CHINESE, "中文 (Chinese)"),
            LanguageOption("fr", Locale.FRENCH, "Français (French)"),
            LanguageOption("ja", Locale.JAPANESE, "日本語 (Japanese)"),
            LanguageOption("ko", Locale.KOREAN, "한국어 (Korean)")
        )

        for (opt in popular) {
            val res = t.isLanguageAvailable(opt.locale)
            list.add(opt.copy(isSupported = res >= TextToSpeech.LANG_AVAILABLE))
        }

        _availableLanguages.value = list
    }

    fun setLanguage(code: String) {
        val t = tts ?: return
        _currentLanguage.value = code
        val locale = when (code) {
            "km" -> KHMER_LOCALE
            "en" -> Locale.US
            "en-GB" -> Locale.UK
            "th" -> Locale("th", "TH")
            "vi" -> Locale("vi", "VN")
            "zh" -> Locale.CHINESE
            "fr" -> Locale.FRENCH
            "ja" -> Locale.JAPANESE
            "ko" -> Locale.KOREAN
            else -> Locale.getDefault()
        }
        val result = t.setLanguage(locale)
        Log.d(TAG, "setLanguage $code result: $result")
    }

    fun setPitch(pitch: Float) {
        _speechPitch.value = pitch
        tts?.setPitch(pitch)
    }

    fun setSpeechRate(rate: Float) {
        _speechRate.value = rate
        tts?.setSpeechRate(rate)
    }

    fun speak(
        text: String,
        utteranceId: String = "somleng_${System.currentTimeMillis()}",
        onDone: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        if (!_isReady.value) {
            onError?.invoke("TTS not ready yet")
            return
        }
        if (text.isBlank()) return

        this.onDoneCallback = onDone
        this.onErrorCallback = onError

        tts?.setPitch(_speechPitch.value)
        tts?.setSpeechRate(_speechRate.value)

        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }

        _isSpeaking.value = true
        val result = tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
        if (result == TextToSpeech.ERROR) {
            _isSpeaking.value = false
            onError?.invoke("Speak failed")
        }
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun synthesizeToFile(
        text: String,
        outFile: File,
        utteranceId: String = "synth_${System.currentTimeMillis()}",
        onDone: (File) -> Unit,
        onError: (String) -> Unit
    ) {
        if (!_isReady.value) {
            onError("TTS engine is not ready")
            return
        }
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(id: String?) {}

            override fun onDone(id: String?) {
                if (id == utteranceId) {
                    onDone(outFile)
                }
            }

            override fun onError(id: String?) {
                if (id == utteranceId) {
                    onError("Failed to synthesize audio file")
                }
            }
        })

        tts?.setPitch(_speechPitch.value)
        tts?.setSpeechRate(_speechRate.value)
        val res = tts?.synthesizeToFile(text, params, outFile, utteranceId)
        if (res == TextToSpeech.ERROR) {
            onError("Synthesis error")
        }
    }

    fun openSystemTtsSettings(): Boolean {
        return try {
            val intent = Intent("com.android.settings.TTS_SETTINGS").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            try {
                val intent = Intent(android.provider.Settings.ACTION_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
                true
            } catch (e2: Exception) {
                false
            }
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        _isReady.value = false
    }
}
