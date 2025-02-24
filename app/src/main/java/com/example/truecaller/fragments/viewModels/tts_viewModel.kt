package com.example.truecaller.fragments.viewModels

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Locale

class TextToSpeechViewModel(application: Application) : AndroidViewModel(application),
    TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private val _isReady = MutableLiveData<Boolean>()
    val isReady: LiveData<Boolean> get() = _isReady

    init {
        tts = TextToSpeech(application, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                _isReady.value = false
                Log.e("TTS", "Language not supported")
            } else {
                _isReady.value = true
                Log.d("TTS", "TTS initialized successfully")
            }
        } else {
            _isReady.value = false
            Log.e("TTS", "Initialization failed")
        }
    }

    fun speak(text: String) {
        if (_isReady.value == true) {
            Log.d("TTS", "Speaking: $text")
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Log.e("TTS", "TTS is not ready yet")
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts?.shutdown()
    }
}
