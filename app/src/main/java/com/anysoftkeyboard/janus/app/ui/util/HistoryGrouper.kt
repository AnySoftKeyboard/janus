package com.anysoftkeyboard.janus.app.ui.util

import android.content.Context
import com.anysoftkeyboard.janus.app.R
import com.anysoftkeyboard.janus.app.ui.data.UiTranslation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object HistoryGrouper {

  fun group(context: Context, items: List<UiTranslation>): Map<String, List<UiTranslation>> {
    // Bolt Optimization: Calculate day boundaries once to avoid Calendar ops inside loop
    val cal = Calendar.getInstance()

    // Start of Today (00:00:00.000)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    val todayStart = cal.timeInMillis

    // Start of Tomorrow (Today + 24h)
    cal.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrowStart = cal.timeInMillis

    // Start of Yesterday (Today - 24h)
    cal.add(Calendar.DAY_OF_YEAR, -2) // Went forward 1, so go back 2
    val yesterdayStart = cal.timeInMillis

    // Optimization: Cache the current month/year string and boundaries
    var cachedMonthStart = -1L
    var cachedMonthEnd = -1L
    var cachedMonthString = ""

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val todayString = context.getString(R.string.history_group_today)
    val yesterdayString = context.getString(R.string.history_group_yesterday)

    return items.groupBy { item ->
      val ts = item.timestamp
      when {
        // Check "Today": [todayStart, tomorrowStart)
        ts >= todayStart && ts < tomorrowStart -> todayString

        // Check "Yesterday": [yesterdayStart, todayStart)
        ts >= yesterdayStart && ts < todayStart -> yesterdayString

        // Older items
        else -> {
          // Bolt Optimization: Check if ts is in the cached month range
          if (ts in cachedMonthStart until cachedMonthEnd) {
            cachedMonthString
          } else {
            cal.timeInMillis = ts

            // Calculate start of this month
            cal.set(Calendar.DAY_OF_MONTH, 1)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            cachedMonthStart = cal.timeInMillis

            // Format string (uses the month set in cal)
            cachedMonthString = monthYearFormat.format(cal.time).uppercase()

            // Calculate start of next month (end of this month range)
            cal.add(Calendar.MONTH, 1)
            cachedMonthEnd = cal.timeInMillis

            cachedMonthString
          }
        }
      }
    }
  }
}
