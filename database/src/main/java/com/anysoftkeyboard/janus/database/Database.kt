package com.anysoftkeyboard.janus.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anysoftkeyboard.janus.database.dao.TranslationDao
import com.anysoftkeyboard.janus.database.entities.Translation

@Database(entities = [Translation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun translationDao(): TranslationDao
}
