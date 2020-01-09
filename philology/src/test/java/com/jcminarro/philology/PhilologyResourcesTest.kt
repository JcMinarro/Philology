package com.jcminarro.philology

import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.When
import org.amshove.kluent.`should equal`
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class PhilologyResourcesTest {

    private val baseResources: Resources = mock()
    private val configuration: Configuration = createConfiguration()
    private val resources = PhilologyResources(baseResources)
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
    fun `Should throw an exception if the given id doesn't exist asking for a text`() {
        configureResourceGetIdException(baseResources, id)
        resources.getText(id)
    }

    @Test
    fun `Should return a CharSequence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id) `should equal` someCharSequence
    }

    @Test
    fun `Should return a defaultCharSequence asking for a text with a default value`() {
        val defaultCharSequence: CharSequence = "default char sequence"
        configureResourceGetIdException(baseResources, id)
        resources.getText(id, defaultCharSequence) `should equal` defaultCharSequence
    }

    @Test
    fun `Should return a CharSequence asking for a text with a default value`() {
        val defaultCharSequence: CharSequence = "default char sequence"
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id, defaultCharSequence) `should equal` someCharSequence
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

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for a quantity text`() {
        configureResourceGetIdException(baseResources, id)
        resources.getQuantityText(id, 1)
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        resources.getQuantityText(id, 1) `should equal` someCharSequence
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for an quantity string`() {
        configureResourceGetIdException(baseResources, id)
        resources.getQuantityString(id, 1)
    }

    @Test
    fun `Should return a CharSequence asking for an quantity string`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        resources.getQuantityString(id, 1) `should equal` someString
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for a string array`() {
        configureResourceGetIdException(baseResources, id)
        resources.getStringArray(id)
    }

    @Test
    fun `Should return a CharSequence asking for a string array`() {
        val textArray = arrayOf<CharSequence>("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        resources.getStringArray(id) `should equal` textArray.map { it.toString() }.toTypedArray()
    }

    @Test(expected = Resources.NotFoundException::class)
    fun `Should throw an exception if the given id doesn't exist asking for a text array`() {
        configureResourceGetIdException(baseResources, id)
        resources.getStringArray(id)
    }

    @Test
    fun `Should return a CharSequence asking for a text array`() {
        val textArray = arrayOf<CharSequence>("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        resources.getTextArray(id) `should equal` textArray
    }

    @Test()
    fun `Should return a CharSequence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getText(id) `should equal` repoCharSequence
    }

    @Test()
    fun `Should return a CharSequence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getString(id) `should equal` repoString
    }

    @Test
    fun `Should return a CharSequence from repository asking for a quantity text`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityText(id, 1) `should equal` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence from repository asking for an quantity string`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityString(id, 1) `should equal` repoString
    }

    @Test
    fun `Should return a CharSequence from repository asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        configurePhilology(createRepository(nameId, textArray = textArray))
        resources.getStringArray(id) `should equal` textArray.map { it.toString() }.toTypedArray()
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        configurePhilology(createRepository(nameId, textArray = textArray))
        resources.getTextArray(id) `should equal` textArray
    }

    @Test
    fun `Should return an XmlResourceParser from base resources asking for an Animation`() {
        val xmlResourceParser = mock<XmlResourceParser>()
        When calling baseResources.getAnimation(id) doReturn xmlResourceParser
        resources.getAnimation(id) `should equal` xmlResourceParser
    }

    @Test
    fun `Should return a DisplayMetrics from base resources asking for a display metrics`() {
        val displayMetrics = mock<DisplayMetrics>()
        When calling baseResources.displayMetrics doReturn displayMetrics
        resources.displayMetrics `should equal` displayMetrics
    }

    @Test
    fun `Should return a Drawable from base resources asking for a drawable for density`() {
        val drawable = mock<Drawable>()
        When calling baseResources.getDrawableForDensity(id, 2) doReturn drawable
        resources.getDrawableForDensity(id, 2) `should equal` drawable
    }
}
