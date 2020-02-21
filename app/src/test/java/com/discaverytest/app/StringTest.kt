package com.discaverytest.app

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StringTest {
    @Test
    fun testStringFormatForIntWithZeroFill() {
        assertEquals("001", "%03d".format(1))
        assertEquals("12", "%02d".format(12))
        assertEquals("312", "%02d".format(312))
        assertEquals("333312", "%02d".format(333312))
    }
}
