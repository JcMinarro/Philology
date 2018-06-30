package com.jcminarro.philology

import android.content.res.Configuration
import android.os.LocaleList
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import java.util.Locale

fun createConfiguration(locale: Locale): Configuration = mock<Configuration>().apply{
    this.locale = locale
    When calling this.locales doReturn LocaleList(locale)
}