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

    // Reset cal for older items formatting
    // We can reuse the same instance

    val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val todayString = context.getString(R.string.history_group_today)
    val yesterdayString = context.getString(R.string.history_group_yesterday)

    // Optimization: Cache formatted strings for month/year to avoid expensive
    // SimpleDateFormat.format calls.
    // Since items are sorted by timestamp (descending), we can just remember the last formatted
    // string and the month/year it belongs to.
    var lastYear = -1
    var lastMonth = -1
    var lastFormattedString = ""

    return items.groupBy { item ->
      val ts = item.timestamp
      when {
        // Check "Today": [todayStart, tomorrowStart)
        ts >= todayStart && ts < tomorrowStart -> todayString

        // Check "Yesterday": [yesterdayStart, todayStart)
        ts >= yesterdayStart && ts < todayStart -> yesterdayString

        // Older items
        else -> {
          cal.timeInMillis = ts
          val year = cal.get(Calendar.YEAR)
          val month = cal.get(Calendar.MONTH)

          if (year == lastYear && month == lastMonth) {
            lastFormattedString
          } else {
            lastYear = year
            lastMonth = month
            lastFormattedString = monthYearFormat.format(cal.time).uppercase()
            lastFormattedString
          }
        }
      }
    }
  }
}
