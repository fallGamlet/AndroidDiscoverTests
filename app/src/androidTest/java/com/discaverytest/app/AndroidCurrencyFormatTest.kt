package com.discaverytest.app

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.text.NumberFormat
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AndroidCurrencyFormatTest {
    @Before
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.discaverytest.app", appContext.packageName)
    }

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
