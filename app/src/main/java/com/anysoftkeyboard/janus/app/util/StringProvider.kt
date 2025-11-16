package com.anysoftkeyboard.janus.app.util

import androidx.annotation.StringRes

/**
 * Interface for providing string resources to non-UI layers like ViewModels and Repositories. This
 * allows for easier testing and better separation of concerns.
 */
interface StringProvider {
  /**
   * Get a string resource by its ID.
   *
   * @param id The string resource ID
   * @return The localized string
   */
  fun getString(@StringRes id: Int): String

  /**
   * Get a formatted string resource by its ID with format arguments.
   *
   * @param id The string resource ID
   * @param formatArgs The format arguments to substitute into the string
   * @return The formatted localized string
   */
  fun getString(@StringRes id: Int, vararg formatArgs: Any): String
}
