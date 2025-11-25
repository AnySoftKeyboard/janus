package com.anysoftkeyboard.janus.app.ui.util

import android.content.Context
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class HistoryGrouperTest {

    private lateinit var mockContext: Context

    @Before
    fun setup() {
        mockContext = mock()
        whenever(mockContext.getString(R.string.history_group_today)).thenReturn("TODAY")
        whenever(mockContext.getString(R.string.history_group_yesterday)).thenReturn("YESTERDAY")
    }

    @Test
    fun `group handles empty list`() {
        val result = HistoryGrouper.group(mockContext, emptyList())
        assertEquals(emptyMap<String, List<UiTranslation>>(), result)
    }

    @Test
    fun `group handles today items`() {
        val today = System.currentTimeMillis()
        val item = createTranslation(today)

        val result = HistoryGrouper.group(mockContext, listOf(item))

        assertEquals(1, result.size)
        assertEquals(listOf(item), result["TODAY"])
    }

    @Test
    fun `group handles yesterday items`() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = cal.timeInMillis
        val item = createTranslation(yesterday)

        val result = HistoryGrouper.group(mockContext, listOf(item))

        assertEquals(1, result.size)
        assertEquals(listOf(item), result["YESTERDAY"])
    }

    @Test
    fun `group handles older items`() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -1) // Last month
        val older = cal.timeInMillis
        val item = createTranslation(older)

        val expectedHeader =
                SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(cal.time).uppercase()

        val result = HistoryGrouper.group(mockContext, listOf(item))

        assertEquals(1, result.size)
        assertEquals(listOf(item), result[expectedHeader])
    }

    @Test
    fun `group handles mixed items`() {
        val today = System.currentTimeMillis()

        val calYesterday = Calendar.getInstance()
        calYesterday.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = calYesterday.timeInMillis

        val calOlder = Calendar.getInstance()
        calOlder.add(Calendar.MONTH, -2)
        val older = calOlder.timeInMillis

        val itemToday = createTranslation(today)
        val itemYesterday = createTranslation(yesterday)
        val itemOlder = createTranslation(older)

        val expectedOlderHeader =
                SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calOlder.time).uppercase()

        val result = HistoryGrouper.group(mockContext, listOf(itemToday, itemYesterday, itemOlder))

        assertEquals(3, result.size)
        assertEquals(listOf(itemToday), result["TODAY"])
        assertEquals(listOf(itemYesterday), result["YESTERDAY"])
        assertEquals(listOf(itemOlder), result[expectedOlderHeader])
    }

    private fun createTranslation(timestamp: Long): UiTranslation {
        return UiTranslation(
                sourceWord = "source",
                sourceLang = "en",
                sourceArticleUrl = "url",
                sourceShortDescription = null,
                sourceSummary = null,
                targetWord = "target",
                targetLang = "es",
                targetArticleUrl = "url",
                targetShortDescription = null,
                targetSummary = null,
                isFavorite = false,
                timestamp = timestamp
        )
    }
}
