package com.anysoftkeyboard.janus.app.ui.util

import android.content.Context
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object HistoryGrouper {

  fun group(context: Context, items: List<UiTranslation>): Map<String, List<UiTranslation>> {
    // Bolt Optimization: Calculate day boundaries once to avoid Calendar ops in loop
    val now = Calendar.getInstance()
    val todayStart = now.clone() as Calendar
    todayStart.set(Calendar.HOUR_OF_DAY, 0)
    todayStart.set(Calendar.MINUTE, 0)
    todayStart.set(Calendar.SECOND, 0)
    todayStart.set(Calendar.MILLISECOND, 0)
    val todayStartMillis = todayStart.timeInMillis

    val yesterdayStart = todayStart.clone() as Calendar
    yesterdayStart.add(Calendar.DAY_OF_YEAR, -1)
    val yesterdayStartMillis = yesterdayStart.timeInMillis

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val itemCalendar = Calendar.getInstance()
    val todayString = context.getString(R.string.history_group_today)
    val yesterdayString = context.getString(R.string.history_group_yesterday)

    return items.groupBy { item ->
      when {
        item.timestamp >= todayStartMillis -> todayString
        item.timestamp >= yesterdayStartMillis -> yesterdayString
        else -> {
          // Fallback to Calendar for older items
          itemCalendar.timeInMillis = item.timestamp
          monthYearFormat.format(itemCalendar.time).uppercase()
        }
      }
    }
  }
}
