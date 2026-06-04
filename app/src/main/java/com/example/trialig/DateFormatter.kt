package com.example.trialig

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    fun format(
        timestamp: Long
    ): String {

        return SimpleDateFormat(
            "d MMM yy   h:mm a",
            Locale.getDefault()
        ).format(
            Date(timestamp)
        )
    }
}