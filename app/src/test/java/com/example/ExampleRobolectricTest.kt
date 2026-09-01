package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.util.KhmerNumberConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Text to សំឡេង", appName)
  }

  @Test
  fun `khmer number conversion tests`() {
    assertEquals("មួយ", KhmerNumberConverter.numberToKhmerWords(1))
    assertEquals("ដប់", KhmerNumberConverter.numberToKhmerWords(10))
    assertEquals("ដប់ប្រាំ", KhmerNumberConverter.numberToKhmerWords(15))
    assertEquals("ម្ភៃ", KhmerNumberConverter.numberToKhmerWords(20))
    assertEquals("១២៣", KhmerNumberConverter.toKhmerDigits("123"))
  }
}

