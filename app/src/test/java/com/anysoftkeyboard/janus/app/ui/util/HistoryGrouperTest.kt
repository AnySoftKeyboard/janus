package com.anysoftkeyboard.janus.app.ui.util

import android.content.Context
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.Calendar

class HistoryGrouperTest {

    @Test
    fun group_groupsCorrectly() {
        val today = Calendar.getInstance()
        val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        val other = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -2) }

        val context = mock<Context> {
            on { getString(R.string.history_group_today) } doReturn "Today"
            on { getString(R.string.history_group_yesterday) } doReturn "Yesterday"
        }

        val items = listOf(
            UiTranslation(
                id = 1,
                sourceLang = "en",
                targetLang = "fr",
                sourceWord = "Hello",
                targetWord = "Bonjour",
                timestamp = today.timeInMillis,
                sourceArticleUrl = "",
                targetArticleUrl = ""
            ),
            UiTranslation(
                id = 2,
                sourceLang = "en",
                targetLang = "fr",
                sourceWord = "Bye",
                targetWord = "Au revoir",
                timestamp = yesterday.timeInMillis,
                sourceArticleUrl = "",
                targetArticleUrl = ""
            ),
            UiTranslation(
                id = 3,
                sourceLang = "en",
                targetLang = "fr",
                sourceWord = "Cat",
                targetWord = "Chat",
                timestamp = other.timeInMillis,
                sourceArticleUrl = "",
                targetArticleUrl = ""
            )
        )

        val grouped = HistoryGrouper.group(context, items)

        assertEquals(3, grouped.size)
        assertEquals(1, grouped["Today"]?.size)
        assertEquals(1, grouped["Yesterday"]?.size)

        val keys = grouped.keys.toList()
        assertEquals("Today", keys[0])
        assertEquals("Yesterday", keys[1])
    }
}
