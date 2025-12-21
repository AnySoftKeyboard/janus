package com.anysoftkeyboard.janus.app.ui.util

import android.content.Context
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object HistoryGrouper {

  fun group(context: Context, items: List<UiTranslation>): Map<String, List<UiTranslation>> {
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val itemCalendar = Calendar.getInstance()

    return items.groupBy { item ->
      itemCalendar.timeInMillis = item.timestamp

      when {
        isSameDay(today, itemCalendar) -> context.getString(R.string.history_group_today)
        isSameDay(yesterday, itemCalendar) -> context.getString(R.string.history_group_yesterday)
        else -> monthYearFormat.format(itemCalendar.time).uppercase()
      }
    }
  }

  private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
  }
}
