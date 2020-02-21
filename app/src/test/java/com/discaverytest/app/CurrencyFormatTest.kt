package com.discaverytest.app

import org.junit.Test

import java.text.NumberFormat
import java.util.*

class CurrencyFormatTest {

    @Test
    @Throws
    fun printDifferenceCurrency() {
        val locales = Locale.getAvailableLocales()
        val currencies = Currency.getAvailableCurrencies()
        val value = 12.75

        currencies
            .map { currency ->
                locales.joinToString("\n") {
                    "${currency.currencyCode} ${currency.symbol} $it \t" + getMoneyText(value, it, currency)
                }
            }
            .forEach { println(it) }

    }

    @Test
    @Throws
    fun printCurrencyByLocale() {
        val value = 12.75
        Locale.getAvailableLocales()
            .map {
                "$it \t" + getMoneyText(value, it)
            }
            .forEach { println(it) }
    }

    private fun getMoneyText(value: Number, locale: Locale, currency: Currency? = null): String {
        return try {
            val formatter = NumberFormat.getCurrencyInstance(locale).also {
                if (currency != null) it.currency = currency
            }
            formatter.format(value.toDouble())
        } catch (e: IllegalArgumentException) {
            "Simple money value $value ${currency?.currencyCode}, because error: $e"
        }
    }

}
