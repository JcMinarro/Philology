package com.jcminarro.philology

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.When
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.calling
import org.amshove.kluent.invoking
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class PhilologyVectorEnabledTintResourcesTest {

    private val baseResources: Resources = mock()
    private val baseContext: Context = mock()
    private val configuration: Configuration = createConfiguration()
    private val resources = PhilologyVectorEnabledTintResources(baseContext, baseResources)
    private val someCharSequence: CharSequence = "text"
    private val someString: String = someCharSequence.toString()
    private val repoCharSequence: CharSequence = "repo"
    private val repoString: String = repoCharSequence.toString()
    private val id = 0
    private val nameId = "nameId"

    @Before
    fun setup() {
        clearPhilology()
        When calling baseResources.configuration doReturn configuration
    }

    @Test
    fun `Should throw an exception if the given id doesn't exit asking for a text`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getText(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSecuence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id) `should be equal to` someCharSequence
    }

    @Test
    fun `Should throw an exception if the given id doesn't exit asking for an String`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getString(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSecuence asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getString(id) `should be equal to` someString
    }

    @Test
    fun `Should return a CharSecuence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getText(id) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSecuence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getString(id) `should be equal to` repoString
    }
}