package com.example.data.util

import java.util.Calendar

object KhmerDateHelper {
    private val KHMER_DAYS = arrayOf(
        "ថ្ងៃអាទិត្យ", "ថ្ងៃចន្ទ", "ថ្ងៃអង្គារ", "ថ្ងៃពុធ", "ថ្ងៃព្រហស្បតិ៍", "ថ្ងៃសុក្រ", "ថ្ងៃសៅរ៍"
    )

    private val KHMER_MONTHS = arrayOf(
        "មករា", "កុម្ភៈ", "មីនា", "មេសា", "ឧសភា", "មិថុនា",
        "កក្កដា", "សីហា", "កញ្ញា", "តុលា", "វិច្ឆិកា", "ធ្នូ"
    )

    fun getTodayKhmerDate(): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) // 0-based
        val year = calendar.get(Calendar.YEAR)
        val buddhistYear = year + 544

        val dayName = KHMER_DAYS[dayOfWeek - 1]
        val monthName = KHMER_MONTHS[month]
        val dayKhmer = KhmerNumberConverter.toKhmerDigits(dayOfMonth.toString())
        val yearKhmer = KhmerNumberConverter.toKhmerDigits(year.toString())
        val beYearKhmer = KhmerNumberConverter.toKhmerDigits(buddhistYear.toString())

        return "$dayName ទី$dayKhmer ខែ$monthName ឆ្នាំ$yearKhmer (ព.ស. $beYearKhmer)"
    }
}
