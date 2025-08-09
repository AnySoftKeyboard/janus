package com.anysoftkeyboard.janus.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translation_history")
data class Translation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    // --- Source Article ---
    @ColumnInfo(index = true) val sourceWord: String,
    @ColumnInfo(index = true) val sourceLangCode: String,
    val sourceArticleUrl: String,
    val sourceShortDescription: String?,
    val sourceSummary: String?,

    // --- Target Article ---
    val translatedWord: String,
    @ColumnInfo(index = true) val targetLangCode: String,
    val targetArticleUrl: String,
    val targetShortDescription: String?,
    val targetSummary: String?,

    // --- Metadata ---
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
