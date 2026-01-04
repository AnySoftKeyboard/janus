package com.anysoftkeyboard.janus.app.ui.util

import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import com.anysoftkeyboard.janus.app.util.StringProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object HistoryGrouper {

  fun group(
      stringProvider: StringProvider,
      items: List<UiTranslation>
  ): Map<String, List<UiTranslation>> {
    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val itemCalendar = Calendar.getInstance()
    val todayString = stringProvider.getString(R.string.history_group_today)
    val yesterdayString = stringProvider.getString(R.string.history_group_yesterday)

    return items.groupBy { item ->
      itemCalendar.timeInMillis = item.timestamp

      when {
        isSameDay(today, itemCalendar) -> todayString
        isSameDay(yesterday, itemCalendar) -> yesterdayString
        else -> monthYearFormat.format(itemCalendar.time).uppercase()
      }
    }
  }

  private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
  }
}
