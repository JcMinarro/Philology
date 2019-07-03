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
    private val quantity = 1
    private val formatArg = "argument"
    private val nameId = "nameId"

    @Before
    fun setup() {
        clearPhilology()
        When calling baseResources.configuration doReturn configuration
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for a text`() {
        configureResourceGetIdException(baseResources, id)
        resources.getText(id)
    }

    @Test
    fun `Should return a CharSequence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id) `should equal` someCharSequence
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for an String`() {
        configureResourceGetIdException(baseResources, id)
        resources.getString(id)
    }

    @Test
    fun `Should return a CharSequence asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getString(id) `should equal` someString
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getText(id) `should equal` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getString(id) `should equal` repoString
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for a quantity text`() {
        configureResourceGetIdException(baseResources, id)
        resources.getQuantityText(id, quantity)
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text`() {
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        resources.getQuantityText(id, quantity) `should equal` someCharSequence
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for an quantity String`() {
        configureResourceGetIdException(baseResources, id)
        resources.getQuantityString(id, quantity)
    }

    @Test
    fun `Should return a CharSequence asking for an quantity String`() {
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        resources.getQuantityString(id, quantity) `should equal` someString
    }

    @Test
    fun `Should return a CharSequence from repository asking for a quantity text`() {
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityText(id, quantity) `should equal` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence from repository asking for an quantity String`() {
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityText(id, quantity) `should equal` repoString
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if id doesn't exist asking for an formatted quantity String`() {
        configureResourceGetIdException(baseResources, id)
        resources.getQuantityString(id, quantity, formatArg)
    }

    @Test
    fun `Should return a CharSequence asking for an formatted quantity String`() {
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, "$someCharSequence%s")
        resources.getQuantityString(id, quantity, formatArg) `should equal` someString + formatArg
    }
}