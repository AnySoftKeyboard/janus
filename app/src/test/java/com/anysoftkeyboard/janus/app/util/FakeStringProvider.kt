package com.anysoftkeyboard.janus.app.util

import androidx.annotation.StringRes

/**
 * Fake implementation of StringProvider for testing. Returns placeholder strings based on the
 * resource ID.
 */
class FakeStringProvider : StringProvider {
  override fun getString(@StringRes id: Int): String {
    return "String resource ID: $id"
  }

  override fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
    return "String resource ID: $id with args: ${formatArgs.joinToString(", ")}"
  }
}
