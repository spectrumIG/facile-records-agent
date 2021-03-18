package it.facile.records.agent.domain.entity.local

import androidx.room.TypeConverter
import java.util.*

/**
 * Type converters to allow Room to reference Calendar data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}