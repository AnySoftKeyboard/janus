package com.anysoftkeyboard.janus.app.ui.util

import android.content.Context
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object HistoryGrouper {

  fun group(context: Context, items: List<UiTranslation>): Map<String, List<UiTranslation>> {
    // Bolt Optimization: Calculate time boundaries once to avoid Calendar operations in loop
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfToday = calendar.timeInMillis

    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val startOfYesterday = calendar.timeInMillis

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val itemCalendar = Calendar.getInstance()
    val todayString = context.getString(R.string.history_group_today)
    val yesterdayString = context.getString(R.string.history_group_yesterday)

    return items.groupBy { item ->
      val timestamp = item.timestamp
      if (timestamp >= startOfToday) {
        todayString
      } else if (timestamp >= startOfYesterday) {
        yesterdayString
      } else {
        itemCalendar.timeInMillis = timestamp
        monthYearFormat.format(itemCalendar.time).uppercase()
      }
    }
  }
}
