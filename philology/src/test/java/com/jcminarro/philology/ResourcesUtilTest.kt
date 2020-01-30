package com.jcminarro.philology

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
import java.util.Locale

class ResourcesUtilTest {

    private var configuration: Configuration = createConfiguration()
    private var quantity = 1
    private val baseResources: Resources = mock()
    private val resources = ResourcesUtil(baseResources)
    private val someCharSequence: CharSequence = "text"
    private val someString: String = someCharSequence.toString()
    private val repoCharSequence: CharSequence = "repo"
    private val repoString: String = repoCharSequence.toString()
    private val id = 0
    private val formatArg = "argument"
    private val nameId = "nameId"

    @Before
    fun setup() {
        clearPhilology()
        When calling baseResources.configuration doReturn configuration
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a text`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getText(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id) `should be equal to` someCharSequence
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an String`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getString(id) } `should throw` Resources.NotFoundException::class
        }

    @Test
    fun `Should return a CharSequence asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getString(id) `should be equal to` someString
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getText(id) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))
        resources.getString(id) `should be equal to` repoString
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a quantity text`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getQuantityText(id, quantity) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text`() {
        configureResourceQuantityString(baseResources, quantity, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        resources.getQuantityText(id, quantity) `should be equal to` someCharSequence
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an quantity String`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getQuantityString(id, quantity) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for an quantity String`() {
        configureResourceQuantityString(baseResources, quantity, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        resources.getQuantityString(id, quantity) `should be equal to` someString
    }

    @Test
    fun `Should throw an exception if id doesn't exist asking for an formatted quantity String`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getQuantityString(id, quantity, formatArg) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for an formatted quantity String`() {
        configureResourceQuantityString(baseResources, quantity, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, "$someCharSequence%s")
        resources.getQuantityString(id, quantity, formatArg) `should be equal to` someString + formatArg
    }

    @Test
    fun `Should return a CharSequence for zero keyword asking for a quantity text`() {
        quantity = 0
        val locale = Locale("ar", "ME")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "zero")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "zero", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence for zero keyword asking for an quantity String`() {
        quantity = 0
        val locale = Locale("ar", "ME")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "zero")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "zero", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoString
    }

    @Test
    fun `Should return a CharSequence for one keyword asking for a quantity text`() {
        quantity = 1
        configureResourceQuantityString(baseResources, quantity, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityText(id, quantity) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence for one keyword asking for an quantity String`() {
        quantity = 1
        configureResourceQuantityString(baseResources, quantity, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityText(id, quantity) `should be equal to` repoString
    }

    @Test
    fun `Should return a CharSequence for two keyword asking for a quantity text`() {
        quantity = 2
        val locale = Locale("ar", "ME")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "two")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "two", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence for two keyword asking for an quantity String`() {
        quantity = 2
        val locale = Locale("ar", "ME")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "two")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "two", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoString
    }

    @Test
    fun `Should return a CharSequence for few keyword asking for a quantity text`() {
        quantity = 2
        val locale = Locale("ru", "RU")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "few")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "few", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence for few keyword asking for an quantity String`() {
        quantity = 2
        val locale = Locale("ru", "RU")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "few")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "few", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoString
    }

    @Test
    fun `Should return a CharSequence for many keyword asking for a quantity text`() {
        quantity = 5
        val locale = Locale("ru", "RU")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "many")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "many", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence for many keyword asking for an quantity String`() {
        quantity = 5
        val locale = Locale("ru", "RU")
        configuration = createConfiguration(locale)
        setup()
        configureResourceQuantityString(baseResources, quantity, "many")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "many", repoCharSequence), locale)
        resources.getQuantityText(id, quantity) `should be equal to` repoString
    }

    @Test
    fun `Should return a CharSequence for other keyword asking for a quantity text`() {
        quantity = 2
        configureResourceQuantityString(baseResources, quantity, "other")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "other", repoCharSequence))
        resources.getQuantityText(id, quantity) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence for other keyword asking for an quantity String`() {
        quantity = 2
        configureResourceQuantityString(baseResources, quantity, "other")
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, "other", repoCharSequence))
        resources.getQuantityText(id, quantity) `should be equal to` repoString
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for text array`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getTextArray(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return an array of strings asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        resources.getTextArray(id) `should be equal to` textArray
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a string array`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getStringArray(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return an array of strings asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        resources.getStringArray(id) `should be equal to` textArray.map { it.toString() }.toTypedArray()
    }

    @Test
    fun `Should return an array of strings from repository asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        configurePhilology(createRepository(nameId, textArray = textArray))
        resources.getTextArray(id) `should be equal to` textArray
    }

    @Test
    fun `Should return an array of strings from repository asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        configurePhilology(createRepository(nameId, textArray = textArray))
        resources.getStringArray(id) `should be equal to` textArray.map { it.toString() }.toTypedArray()
    }
}
