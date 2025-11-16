package com.anysoftkeyboard.janus.app.util

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/** Android implementation of StringProvider that uses Context to retrieve string resources. */
@Singleton
class AndroidStringProvider @Inject constructor(@ApplicationContext private val context: Context) :
    StringProvider {
  override fun getString(@StringRes id: Int): String {
    return context.getString(id)
  }

  override fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
    return context.getString(id, *formatArgs)
  }
}
