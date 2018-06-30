package com.jcminarro.philology

import android.content.res.Configuration
import android.content.res.Resources
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import org.amshove.kluent.When
import org.amshove.kluent.`should equal`
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import java.util.Locale

class ResourcesUtilTest {

    private val baseResources: Resources = mock()
    private val configuration: Configuration = createConfiguration(Locale.ENGLISH)
    private val resources = ResourcesUtil(baseResources)
    private val someCharSequence: CharSequence = "text"
    private val someString: String = someCharSequence.toString()
    private val id = 0

    @Before
    fun setup() {
        When calling baseResources.configuration doReturn configuration
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exit asking for a text`() {
        When calling baseResources.getText(id) doThrow Resources.NotFoundException()
        resources.getText(id)
    }

    @Test()
    fun `Should return a CharSecuence asking for a text`() {
        When calling baseResources.getText(id) doReturn someCharSequence
        resources.getText(id) `should equal` someCharSequence
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exit asking for an String`() {
        When calling baseResources.getText(id) doThrow Resources.NotFoundException()
        resources.getString(id)
    }

    @Test()
    fun `Should return a CharSecuence asking for an String`() {
        When calling baseResources.getText(id) doReturn someCharSequence
        resources.getString(id) `should equal` someString
    }
}