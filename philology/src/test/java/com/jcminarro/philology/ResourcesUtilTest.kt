package com.jcminarro.philology

import android.content.res.Configuration
import android.content.res.Resources
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.When
import org.amshove.kluent.`should equal`
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class ResourcesUtilTest {

    private val baseResources: Resources = mock()
    private val configuration: Configuration = createConfiguration()
    private val resources = ResourcesUtil(baseResources)
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

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exit asking for a text`() {
        configureResourceGetTextException(baseResources, id)
        resources.getText(id)
    }

    @Test()
    fun `Should return a CharSecuence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id) `should equal` someCharSequence
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exit asking for an String`() {
        configureResourceGetTextException(baseResources, id)
        resources.getString(id)
    }

    @Test()
    fun `Should return a CharSecuence asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getString(id) `should equal` someString
    }

    @Test()
    fun `Should return a CharSecuence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, repoCharSequence))
        resources.getText(id) `should equal` repoCharSequence
    }

    @Test()
    fun `Should return a CharSecuence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, repoCharSequence))
        resources.getString(id) `should equal` repoString
    }
}