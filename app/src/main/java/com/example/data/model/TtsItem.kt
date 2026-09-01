package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tts_history")
data class TtsItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val languageCode: String = "km",
    val languageDisplayName: String = "ភាសាខ្មែរ (Khmer)",
    val pitch: Float = 1.0f,
    val speed: Float = 1.0f,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val category: String = "General"
)
