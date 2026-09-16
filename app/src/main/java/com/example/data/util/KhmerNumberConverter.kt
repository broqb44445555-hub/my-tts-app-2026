package com.example.data.util

object KhmerNumberConverter {
    private val KHMER_DIGITS = mapOf(
        '0' to '០', '1' to '១', '2' to '២', '3' to '៣', '4' to '៤',
        '5' to '៥', '6' to '៦', '7' to '៧', '8' to '៨', '9' to '៩'
    )

    private val ARABIC_DIGITS = mapOf(
        '០' to '0', '១' to '1', '២' to '2', '៣' to '3', '៤' to '4',
        '៥' to '5', '៦' to '6', '៧' to '7', '៨' to '8', '៩' to '9'
    )

    private val ONES = arrayOf("", "មួយ", "ពីរ", "បី", "បួន", "ប្រាំ", "ប្រាំមួយ", "ប្រាំពីរ", "ប្រាំបី", "ប្រាំបួន")
    private val TENS = arrayOf("", "ដប់", "ម្ភៃ", "សាមសិប", "សែសិប", "ហាសិប", "ហុកសិប", "ចិតសិប", "ប៉ែតសិប", "កៅសិប")

    fun toKhmerDigits(input: String): String {
        return input.map { KHMER_DIGITS[it] ?: it }.joinToString("")
    }

    fun toArabicDigits(input: String): String {
        return input.map { ARABIC_DIGITS[it] ?: it }.joinToString("")
    }

    fun numberToKhmerWords(num: Long): String {
        if (num == 0L) return "សូន្យ"
        if (num < 0) return "ដក " + numberToKhmerWords(-num)

        var n = num
        val parts = mutableListOf<String>()

        // Millions (លាន)
        val millions = n / 1_000_000
        if (millions > 0) {
            parts.add(convertUnderMillion(millions) + "លាន")
            n %= 1_000_000
        }

        // Hundred thousands (សែន)
        val hundredThousands = n / 100_000
        if (hundredThousands > 0) {
            parts.add(convertUnderMillion(hundredThousands) + "សែន")
            n %= 100_000
        }

        // Ten thousands (ម៉ឺន)
        val tenThousands = n / 10_000
        if (tenThousands > 0) {
            parts.add(convertUnderMillion(tenThousands) + "ម៉ឺន")
            n %= 10_000
        }

        // Thousands (ពាន់)
        val thousands = n / 1_000
        if (thousands > 0) {
            parts.add(ONES[thousands.toInt()] + "ពាន់")
            n %= 1_000
        }

        // Hundreds (រយ)
        val hundreds = n / 100
        if (hundreds > 0) {
            parts.add(ONES[hundreds.toInt()] + "រយ")
            n %= 100
        }

        // Tens & Ones
        if (n > 0) {
            if (n < 10) {
                parts.add(ONES[n.toInt()])
            } else if (n in 10..19) {
                if (n == 10L) {
                    parts.add("ដប់")
                } else {
                    parts.add("ដប់" + ONES[(n % 10).toInt()])
                }
            } else {
                val tensDigit = (n / 10).toInt()
                val onesDigit = (n % 10).toInt()
                if (onesDigit == 0) {
                    parts.add(TENS[tensDigit])
                } else {
                    parts.add(TENS[tensDigit] + ONES[onesDigit])
                }
            }
        }

        return parts.joinToString("")
    }

    private fun convertUnderMillion(num: Long): String {
        return numberToKhmerWords(num)
    }

    fun formatKhmerCurrency(amount: Long): String {
        val words = numberToKhmerWords(amount)
        val khmerNum = toKhmerDigits(amount.toString())
        return "$khmerNum ៛ ($words រៀល)"
    }

    fun readAsRiel(amount: Long): String {
        val words = numberToKhmerWords(amount)
        return "$words រៀល"
    }

    fun readAsUsd(amount: Long): String {
        val words = numberToKhmerWords(amount)
        return "$words ដុល្លារ"
    }

    fun readAsYear(year: Long): String {
        val words = numberToKhmerWords(year)
        return "ឆ្នាំ $words"
    }

    fun readDigitByDigit(input: String): String {
        val clean = toArabicDigits(input).filter { it.isDigit() }
        val digitNames = arrayOf("សូន្យ", "មួយ", "ពីរ", "បី", "បួន", "ប្រាំ", "ប្រាំមួយ", "ប្រាំពីរ", "ប្រាំបី", "ប្រាំបួន")
        return clean.mapNotNull { ch ->
            val d = ch.digitToIntOrNull()
            if (d != null && d in 0..9) digitNames[d] else null
        }.joinToString(" ")
    }
}
