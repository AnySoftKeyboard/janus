package com.anysoftkeyboard.janus.app.util

import androidx.annotation.StringRes
import com.anysoftkeyboard.janus.app.R
import javax.inject.Inject
import kotlin.random.Random

/**
 * Data class holding a triplet of messages:
 * 1. The initial welcome message shown in the empty state.
 * 2. The loading message shown while searching.
 * 3. The instruction message shown in the search results.
 */
data class TranslationFlowMessages(
    @StringRes val welcomeMessageResId: Int,
    @StringRes val loadingMessageResId: Int,
    @StringRes val searchInstructionResId: Int
)

/** Provides random welcome messages and their corresponding search instructions. */
class TranslationFlowMessagesProvider @Inject constructor() {

  private val messages =
      listOf(
          TranslationFlowMessages(
              R.string.empty_state_initial,
              R.string.loading_state_initial,
              R.string.search_instruction_initial),
          TranslationFlowMessages(
              R.string.empty_state_initial_1,
              R.string.loading_state_initial_1,
              R.string.search_instruction_initial_1),
          TranslationFlowMessages(
              R.string.empty_state_initial_2,
              R.string.loading_state_initial_2,
              R.string.search_instruction_initial_2),
          TranslationFlowMessages(
              R.string.empty_state_initial_3,
              R.string.loading_state_initial_3,
              R.string.search_instruction_initial_3),
          TranslationFlowMessages(
              R.string.empty_state_initial_4,
              R.string.loading_state_initial_4,
              R.string.search_instruction_initial_4),
          TranslationFlowMessages(
              R.string.empty_state_initial_5,
              R.string.loading_state_initial_5,
              R.string.search_instruction_initial_5),
          TranslationFlowMessages(
              R.string.empty_state_bridge,
              R.string.loading_state_bridge,
              R.string.search_instruction_bridge),
          TranslationFlowMessages(
              R.string.empty_state_scholar,
              R.string.loading_state_scholar,
              R.string.search_instruction_scholar))

  /** Returns a random [TranslationFlowMessages] from the predefined list. */
  fun getRandomMessage(): TranslationFlowMessages {
    return messages[Random.nextInt(messages.size)]
  }
}
