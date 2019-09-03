package com.jcminarro.philology

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.When
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class PhilologyContextWrapperTest {

    val baseContext: Context = mock()
    val baseResources: Resources = mock()
    private val philologyContextWrapper = PhilologyContextWrapper(baseContext)

    @Before
    fun setup() {
        When calling baseContext.resources doReturn baseResources
    }

    @Test
    fun `Should return a PhilologyResources`() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(false)
        philologyContextWrapper.resources `should be instance of` PhilologyResources::class
    }

    @Test
    fun `Should return a PhilologyVectorEnabledTintResources`() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        philologyContextWrapper.resources `should be instance of` PhilologyVectorEnabledTintResources::class
    }
}