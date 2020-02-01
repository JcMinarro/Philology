package com.jcminarro.philology

import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.Verify
import org.amshove.kluent.When
import org.amshove.kluent.`Verify no further interactions`
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.invoking
import org.amshove.kluent.mock
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class PhilologyVectorEnabledTintResourcesTest {

    private val configuration: Configuration = createConfiguration()
    private val baseResources: Resources = mock()
    private val resourcesUtil = spy(ResourcesUtil(baseResources))
    private val philologyResources = PhilologyVectorEnabledTintResources(mock(), resourcesUtil).also {
        Mockito.reset(baseResources, resourcesUtil)
    }
    private val someCharSequence: CharSequence = randomString()
    private val someString: String = someCharSequence.toString()
    private val repoCharSequence: CharSequence = randomString()
    private val repoString: String = repoCharSequence.toString()
    private val id = randomInt()
    private val formatArg = randomString()
    private val nameId = randomString()

    @Before
    fun setup() {
        clearPhilology()
        When calling baseResources.configuration doReturn configuration
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a text`() {
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getText(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getText(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)

        val result = philologyResources.getText(id)

        result `should be equal to` someCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        Verify on resourcesUtil that resourcesUtil.getText(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))

        val result = philologyResources.getText(id)

        result `should be equal to` repoCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getText(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a text with default value`() {
        val defaultCharSequence = randomString()
        configureResourceGetText(baseResources, id, nameId, someCharSequence)

        val result = philologyResources.getText(id, defaultCharSequence)

        result `should be equal to` someCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        Verify on resourcesUtil that resourcesUtil.getText(id, defaultCharSequence) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text with default value`() {
        val defaultCharSequence = randomString()
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))

        val result = philologyResources.getText(id, defaultCharSequence)

        result `should be equal to` repoCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getText(id, defaultCharSequence) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a the default CharSequence asking for a text`() {
        val defaultCharSequence: CharSequence = randomString()
        configureResourceGetIdException(baseResources, id)

        val result = philologyResources.getText(id, defaultCharSequence)

        result `should be equal to` defaultCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getText(id, defaultCharSequence) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an String`() {
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getString(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getString(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)

        val result = philologyResources.getString(id)

        result `should be equal to` someString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        Verify on resourcesUtil that resourcesUtil.getString(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))

        val result = philologyResources.getString(id)

        result `should be equal to` repoString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getString(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a formatted String`() {
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getString(id, formatArg) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getString(id, formatArg) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a formatted String`() {
        configureResourceGetText(baseResources, id, nameId, "$someCharSequence%s")

        val result = philologyResources.getString(id, formatArg)

        result `should be equal to` "$someString$formatArg"
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        Verify on resourcesUtil that resourcesUtil.getString(id, formatArg) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence from repository asking for a formatted String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, "$repoCharSequence%s"))

        val result = philologyResources.getString(id, formatArg)

        result `should be equal to` "$repoString$formatArg"
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getString(id, formatArg) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a quantity text`() {
        val quantity = randomInt()
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getQuantityText(id, quantity) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getQuantityText(id, quantity) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text`() {
        val quantity = randomInt()
        configureResourceQuantityString(baseResources, quantity, randomString())
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)

        val result = philologyResources.getQuantityText(id, quantity)

        result `should be equal to` someCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on baseResources that baseResources.getQuantityText(id, quantity) was called
        Verify on resourcesUtil that resourcesUtil.getQuantityText(id, quantity) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text from repository`() {
        val quantity = randomInt()
        val quantityString = randomString()
        configureResourceQuantityString(baseResources, quantity, quantityString)
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, quantityString, repoCharSequence))

        val result = philologyResources.getQuantityText(id, quantity)

        result `should be equal to` repoCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on resourcesUtil that resourcesUtil.getQuantityText(id, quantity) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an quantity String`() {
        val quantity = randomInt()
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getQuantityString(id, quantity) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getQuantityString(id, quantity) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for an quantity String`() {
        val quantity = randomInt()
        configureResourceQuantityString(baseResources, quantity, randomString())
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)

        val result = philologyResources.getQuantityString(id, quantity)

        result `should be equal to` someString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on baseResources that baseResources.getQuantityText(id, quantity) was called
        Verify on resourcesUtil that resourcesUtil.getQuantityString(id, quantity) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for an quantity String from repository`() {
        val quantity = randomInt()
        val quantityString = randomString()
        configureResourceQuantityString(baseResources, quantity, quantityString)
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, quantityString, repoCharSequence))

        val result = philologyResources.getQuantityString(id, quantity)

        result `should be equal to` repoString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on resourcesUtil that resourcesUtil.getQuantityString(id, quantity) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if id doesn't exist asking for a formatted quantity String`() {
        configureResourceGetIdException(baseResources, id)
        val quantity = randomInt()

        invoking { philologyResources.getQuantityString(id, quantity, formatArg) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getQuantityString(id, quantity, formatArg) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a formatted quantity String`() {
        val quantity = randomInt()
        configureResourceQuantityString(baseResources, quantity, randomString())
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, "$someCharSequence%s")

        val result = philologyResources.getQuantityString(id, quantity, formatArg)

        result `should be equal to` someString + formatArg
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on baseResources that baseResources.getQuantityText(id, quantity) was called
        Verify on resourcesUtil that resourcesUtil.getQuantityString(id, quantity, formatArg) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return a CharSequence asking for a formatted quantity text from repository`() {
        val quantity = randomInt()
        val quantityString = randomString()
        configureResourceQuantityString(baseResources, quantity, quantityString)
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, quantityString, "$repoCharSequence%s"))

        val result = philologyResources.getQuantityString(id, quantity, formatArg)

        result `should be equal to` "$repoCharSequence$formatArg"
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on resourcesUtil that resourcesUtil.getQuantityString(id, quantity, formatArg) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for text array`() {
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getTextArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getTextArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return an array of strings asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        configureResourceGetTextArray(baseResources, id, nameId, textArray)

        val result = philologyResources.getTextArray(id)

        result `should be equal to` textArray
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getTextArray(id) was called
        Verify on resourcesUtil that resourcesUtil.getTextArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return an array of strings from repository asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        configureResourceGetTextArray(baseResources, id, nameId, arrayOf(randomString(), randomString()))
        configurePhilology(createRepository(nameId, textArray = textArray))

        val result = philologyResources.getTextArray(id)

        result `should be equal to` textArray
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getTextArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a string array`() {
        configureResourceGetIdException(baseResources, id)

        invoking { philologyResources.getStringArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getStringArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return an array of strings asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        val expectedResult = textArray.map { it.toString() }.toTypedArray()
        configureResourceGetTextArray(baseResources, id, nameId, textArray)

        val result = philologyResources.getStringArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getTextArray(id) was called
        Verify on resourcesUtil that resourcesUtil.getStringArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return an array of strings from repository asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        val expectedResult = textArray.map { it.toString() }.toTypedArray()
        configureResourceGetTextArray(baseResources, id, nameId, arrayOf(randomString(), randomString()))
        configurePhilology(createRepository(nameId, textArray = textArray))

        val result = philologyResources.getStringArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getStringArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getAnimation`() {
        val id = randomInt()
        When calling baseResources.getAnimation(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getAnimation(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getAnimation(id) was called
        Verify on resourcesUtil that resourcesUtil.getAnimation(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return animation calling getAnimation`() {
        val id = randomInt()
        val expectedResult: XmlResourceParser = mock()
        When calling baseResources.getAnimation(id) doReturn expectedResult

        val result = philologyResources.getAnimation(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getAnimation(id) was called
        Verify on resourcesUtil that resourcesUtil.getAnimation(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return displayMetrics calling getDisplayMetrics`() {
        val expectedResult: DisplayMetrics = mock()
        When calling baseResources.displayMetrics doReturn expectedResult

        val result = philologyResources.getDisplayMetrics()

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.displayMetrics was called
        Verify on resourcesUtil that resourcesUtil.getDisplayMetrics() was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        When calling baseResources.getDrawableForDensity(id, density) doThrow Resources
                .NotFoundException::class

        invoking { philologyResources.getDrawableForDensity(id, density) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getDrawableForDensity(id, density) was called
        Verify on resourcesUtil that resourcesUtil.getDrawableForDensity(id, density) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Drawable calling getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawableForDensity(id, density) doReturn expectedResult

        val result = philologyResources.getDrawableForDensity(id, density)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawableForDensity(id, density) was called
        Verify on resourcesUtil that resourcesUtil.getDrawableForDensity(id, density) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling  getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getDrawableForDensity(id, density, theme) doThrow Resources
                .NotFoundException::class

        invoking { philologyResources.getDrawableForDensity(id, density, theme) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDrawableForDensity(id, density, theme) was called
        Verify on resourcesUtil that resourcesUtil.getDrawableForDensity(id, density, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Drawable calling  getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawableForDensity(id, density, theme) doReturn expectedResult

        val result = philologyResources.getDrawableForDensity(id, density, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawableForDensity(id, density, theme) was called
        Verify on resourcesUtil that resourcesUtil.getDrawableForDensity(id, density, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return configuration calling getConfiguration`() {
        val result = philologyResources.getConfiguration()

        result `should be equal to` configuration
        Verify on baseResources that baseResources.configuration was called
        Verify on resourcesUtil that resourcesUtil.getConfiguration() was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception calling obtainAttributes`() {
        val set: AttributeSet = mock()
        val attrs: IntArray = intArrayOf(randomInt())
        When calling baseResources.obtainAttributes(set, attrs) doThrow Resources.NotFoundException::class

        invoking { philologyResources.obtainAttributes(set, attrs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.obtainAttributes(set, attrs) was called
        Verify on resourcesUtil that resourcesUtil.obtainAttributes(set, attrs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return animation calling obtainAttributes`() {
        val set: AttributeSet = mock()
        val attrs: IntArray = intArrayOf(randomInt())
        val expectedResult: TypedArray = mock()
        When calling baseResources.obtainAttributes(set, attrs) doReturn expectedResult

        val result = philologyResources.obtainAttributes(set, attrs)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.obtainAttributes(set, attrs) was called
        Verify on resourcesUtil that resourcesUtil.obtainAttributes(set, attrs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling obtainTypedArray`() {
        val id = randomInt()
        When calling baseResources.obtainTypedArray(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.obtainTypedArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.obtainTypedArray(id) was called
        Verify on resourcesUtil that resourcesUtil.obtainTypedArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return TypedArray calling obtainTypedArray`() {
        val id = randomInt()
        val expectedResult: TypedArray = mock()
        When calling baseResources.obtainTypedArray(id) doReturn expectedResult

        val result = resourcesUtil.obtainTypedArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.obtainTypedArray(id) was called
        Verify on resourcesUtil that resourcesUtil.obtainTypedArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDimensionPixelSize`() {
        val id = randomInt()
        When calling baseResources.getDimensionPixelSize(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getDimensionPixelSize(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDimensionPixelSize(id) was called
        Verify on resourcesUtil that resourcesUtil.getDimensionPixelSize(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return dimension calling getDimensionPixelSize`() {
        val id = randomInt()
        val expectedResult: Int = randomInt()
        When calling baseResources.getDimensionPixelSize(id) doReturn expectedResult

        val result = philologyResources.getDimensionPixelSize(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDimensionPixelSize(id) was called
        Verify on resourcesUtil that resourcesUtil.getDimensionPixelSize(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getIntArray`() {
        val id = randomInt()
        When calling baseResources.getIntArray(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getIntArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getIntArray(id) was called
        Verify on resourcesUtil that resourcesUtil.getIntArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return intArray calling getIntArray`() {
        val id = randomInt()
        val expectedResult: IntArray = intArrayOf(randomInt(), randomInt(), randomInt())
        When calling baseResources.getIntArray(id) doReturn expectedResult

        val result = philologyResources.getIntArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getIntArray(id) was called
        Verify on resourcesUtil that resourcesUtil.getIntArray(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getValue`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()
        When calling baseResources.getValue(id, typedValue, resolveRefs) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getValue(id, typedValue, resolveRefs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getValue(id, typedValue, resolveRefs) was called
        Verify on resourcesUtil that resourcesUtil.getValue(id, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return value calling getValue`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()

        philologyResources.getValue(id, typedValue, resolveRefs)

        Verify on baseResources that baseResources.getValue(id, typedValue, resolveRefs) was called
        Verify on resourcesUtil that resourcesUtil.getValue(id, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given name doesn't exist calling getValue`() {
        val name = randomString()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()
        When calling baseResources.getValue(name, typedValue, resolveRefs) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getValue(name, typedValue, resolveRefs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getValue(name, typedValue, resolveRefs) was called
        Verify on resourcesUtil that resourcesUtil.getValue(name, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return value  calling getValue`() {
        val name = randomString()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()

        philologyResources.getValue(name, typedValue, resolveRefs)

        Verify on baseResources that baseResources.getValue(name, typedValue, resolveRefs) was called
        Verify on resourcesUtil that resourcesUtil.getValue(name, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourcePackageName`() {
        val id = randomInt()
        When calling baseResources.getResourcePackageName(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getResourcePackageName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourcePackageName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourcePackageName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return package calling getResourcePackageName`() {
        val id = randomInt()
        val expectedResult: String = randomString()
        When calling baseResources.getResourcePackageName(id) doReturn expectedResult

        val result = philologyResources.getResourcePackageName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourcePackageName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourcePackageName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling openRawResourceFd`() {
        val id = randomInt()
        When calling baseResources.openRawResourceFd(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.openRawResourceFd(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.openRawResourceFd(id) was called
        Verify on resourcesUtil that resourcesUtil.openRawResourceFd(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return assetFileDescriptor calling openRawResourceFd`() {
        val id = randomInt()
        val expectedResult: AssetFileDescriptor = mock()
        When calling baseResources.openRawResourceFd(id) doReturn expectedResult

        val result = philologyResources.openRawResourceFd(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.openRawResourceFd(id) was called
        Verify on resourcesUtil that resourcesUtil.openRawResourceFd(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDimension`() {
        val id = randomInt()
        When calling baseResources.getDimension(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getDimension(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDimension(id) was called
        Verify on resourcesUtil that resourcesUtil.getDimension(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return dimension calling getDimension`() {
        val id = randomInt()
        val expectedResult = randomFloat()
        When calling baseResources.getDimension(id) doReturn expectedResult

        val result = philologyResources.getDimension(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDimension(id) was called
        Verify on resourcesUtil that resourcesUtil.getDimension(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getColorStateList`() {
        val id = randomInt()
        When calling baseResources.getColorStateList(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getColorStateList(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getColorStateList(id) was called
        Verify on resourcesUtil that resourcesUtil.getColorStateList(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return colorStateList calling getColorStateList`() {
        val id = randomInt()
        val expectedResult: ColorStateList = mock()
        When calling baseResources.getColorStateList(id) doReturn expectedResult

        val result = philologyResources.getColorStateList(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColorStateList(id) was called
        Verify on resourcesUtil that resourcesUtil.getColorStateList(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling getColorStateList`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getColorStateList(id, theme) doThrow Resources
                .NotFoundException::class

        invoking { philologyResources.getColorStateList(id, theme) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getColorStateList(id, theme) was called
        Verify on resourcesUtil that resourcesUtil.getColorStateList(id, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return colorStateList  calling getColorStateList`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult: ColorStateList = mock()
        When calling baseResources.getColorStateList(id, theme) doReturn expectedResult

        val result = philologyResources.getColorStateList(id, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColorStateList(id, theme) was called
        Verify on resourcesUtil that resourcesUtil.getColorStateList(id, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getBoolean`() {
        val id = randomInt()
        When calling baseResources.getBoolean(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getBoolean(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getBoolean(id) was called
        Verify on resourcesUtil that resourcesUtil.getBoolean(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return boolean calling getBoolean`() {
        val id = randomInt()
        val expectedResult = randomBoolean()
        When calling baseResources.getBoolean(id) doReturn expectedResult

        val result = philologyResources.getBoolean(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getBoolean(id) was called
        Verify on resourcesUtil that resourcesUtil.getBoolean(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getIdentifier`() {
        val name = randomString()
        val defType = randomString()
        val defPackage = randomString()
        When calling baseResources.getIdentifier(name, defType, defPackage) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getIdentifier(name, defType, defPackage) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getIdentifier(name, defType, defPackage) was called
        Verify on resourcesUtil that resourcesUtil.getIdentifier(name, defType, defPackage) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return integer calling getIdentifier`() {
        val name = randomString()
        val defType = randomString()
        val defPackage = randomString()
        val expectedResult = randomInt()
        When calling baseResources.getIdentifier(name, defType, defPackage) doReturn expectedResult

        val result = philologyResources.getIdentifier(name, defType, defPackage)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getIdentifier(name, defType, defPackage) was called
        Verify on resourcesUtil that resourcesUtil.getIdentifier(name, defType, defPackage) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getColor`() {
        val id = randomInt()
        When calling baseResources.getColor(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getColor(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getColor(id) was called
        Verify on resourcesUtil that resourcesUtil.getColor(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return color calling getColor`() {
        val id = randomInt()
        val expectedResult = randomInt()
        When calling baseResources.getColor(id) doReturn expectedResult

        val result = philologyResources.getColor(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColor(id) was called
        Verify on resourcesUtil that resourcesUtil.getColor(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling getColor`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getColor(id, theme) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getColor(id, theme) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getColor(id, theme) was called
        Verify on resourcesUtil that resourcesUtil.getColor(id, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return color  calling getColor`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult = randomInt()
        When calling baseResources.getColor(id, theme) doReturn expectedResult

        val result = philologyResources.getColor(id, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColor(id, theme) was called
        Verify on resourcesUtil that resourcesUtil.getColor(id, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should call to updateConfiguration`() {
        val newConf: Configuration = mock()
        val newMetrics: DisplayMetrics = mock()

        philologyResources.updateConfiguration(newConf, newMetrics)

        Verify on baseResources that baseResources.updateConfiguration(newConf, newMetrics) was called
        Verify on resourcesUtil that resourcesUtil.updateConfiguration(newConf, newMetrics) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling openRawResource`() {
        val id = randomInt()
        When calling baseResources.openRawResource(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.openRawResource(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.openRawResource(id) was called
        Verify on resourcesUtil that resourcesUtil.openRawResource(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return inputStream calling openRawResource`() {
        val id = randomInt()
        val expectedResult: InputStream = mock()
        When calling baseResources.openRawResource(id) doReturn expectedResult

        val result = philologyResources.openRawResource(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.openRawResource(id) was called
        Verify on resourcesUtil that resourcesUtil.openRawResource(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling openRawResource`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        When calling baseResources.openRawResource(id, typedValue) doThrow Resources
                .NotFoundException::class

        invoking { philologyResources.openRawResource(id, typedValue) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.openRawResource(id, typedValue) was called
        Verify on resourcesUtil that resourcesUtil.openRawResource(id, typedValue) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return inputStream  calling openRawResource`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        val expectedResult: InputStream = mock()
        When calling baseResources.openRawResource(id, typedValue) doReturn expectedResult

        val result = philologyResources.openRawResource(id, typedValue)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.openRawResource(id, typedValue) was called
        Verify on resourcesUtil that resourcesUtil.openRawResource(id, typedValue) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getMovie`() {
        val id = randomInt()
        When calling baseResources.getMovie(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getMovie(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getMovie(id) was called
        Verify on resourcesUtil that resourcesUtil.getMovie(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Movie calling getMovie`() {
        val id = randomInt()
        val expectedResult: Movie = mock()
        When calling baseResources.getMovie(id) doReturn expectedResult

        val result = philologyResources.getMovie(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getMovie(id) was called
        Verify on resourcesUtil that resourcesUtil.getMovie(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getInteger`() {
        val id = randomInt()
        When calling baseResources.getInteger(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getInteger(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getInteger(id) was called
        Verify on resourcesUtil that resourcesUtil.getInteger(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Integer calling getInteger`() {
        val id = randomInt()
        val expectedResult = randomInt()
        When calling baseResources.getInteger(id) doReturn expectedResult

        val result = philologyResources.getInteger(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getInteger(id) was called
        Verify on resourcesUtil that resourcesUtil.getInteger(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an XmlPullParserException if the given id doesn't exist calling parseBundleExtras`() {
        val parser: XmlResourceParser = mock()
        val outBundle: Bundle = mock()
        When calling baseResources.parseBundleExtras(parser, outBundle) doThrow XmlPullParserException::class

        invoking { philologyResources.parseBundleExtras(parser, outBundle) } `should throw` XmlPullParserException::class

        Verify on baseResources that baseResources.parseBundleExtras(parser, outBundle) was called
        Verify on resourcesUtil that resourcesUtil.parseBundleExtras(parser, outBundle) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an IOException if the given id doesn't exist calling parseBundleExtras`() {
        val parser: XmlResourceParser = mock()
        val outBundle: Bundle = mock()
        When calling baseResources.parseBundleExtras(parser, outBundle) doThrow IOException::class

        invoking { philologyResources.parseBundleExtras(parser, outBundle) } `should throw` IOException::class

        Verify on baseResources that baseResources.parseBundleExtras(parser, outBundle) was called
        Verify on resourcesUtil that resourcesUtil.parseBundleExtras(parser, outBundle) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Integer calling parseBundleExtras`() {
        val parser: XmlResourceParser = mock()
        val outBundle: Bundle = mock()

        philologyResources.parseBundleExtras(parser, outBundle)

        Verify on baseResources that baseResources.parseBundleExtras(parser, outBundle) was called
        Verify on resourcesUtil that resourcesUtil.parseBundleExtras(parser, outBundle) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDrawable`() {
        val id = randomInt()
        When calling baseResources.getDrawable(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getDrawable(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDrawable(id) was called
        Verify on resourcesUtil that resourcesUtil.getDrawable(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Drawable calling getDrawable`() {
        val id = randomInt()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawable(id) doReturn expectedResult

        val result = philologyResources.getDrawable(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawable(id) was called
        Verify on resourcesUtil that resourcesUtil.getDrawable(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling getDrawable`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getDrawable(id, theme) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getDrawable(id, theme) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getDrawable(id, theme) was called
        Verify on resourcesUtil that resourcesUtil.getDrawable(id, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Drawable  calling getDrawable`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawable(id, theme) doReturn expectedResult

        val result = philologyResources.getDrawable(id, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawable(id, theme) was called
        Verify on resourcesUtil that resourcesUtil.getDrawable(id, theme) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourceTypeName`() {
        val id = randomInt()
        When calling baseResources.getResourceTypeName(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getResourceTypeName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourceTypeName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourceTypeName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Name calling getResourceTypeName`() {
        val id = randomInt()
        val expectedResult = randomString()
        When calling baseResources.getResourceTypeName(id) doReturn expectedResult

        val result = philologyResources.getResourceTypeName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourceTypeName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourceTypeName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getLayout`() {
        val id = randomInt()
        When calling baseResources.getLayout(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getLayout(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getLayout(id) was called
        Verify on resourcesUtil that resourcesUtil.getLayout(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return layout calling getLayout`() {
        val id = randomInt()
        val expectedResult: XmlResourceParser = mock()
        When calling baseResources.getLayout(id) doReturn expectedResult

        val result = philologyResources.getLayout(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getLayout(id) was called
        Verify on resourcesUtil that resourcesUtil.getLayout(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getFont`() {
        val id = randomInt()
        When calling baseResources.getFont(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getFont(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getFont(id) was called
        Verify on resourcesUtil that resourcesUtil.getFont(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return typeFace calling getFont`() {
        val id = randomInt()
        val expectedResult: Typeface = mock()
        When calling baseResources.getFont(id) doReturn expectedResult

        val result = philologyResources.getFont(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getFont(id) was called
        Verify on resourcesUtil that resourcesUtil.getFont(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getXml`() {
        val id = randomInt()
        When calling baseResources.getXml(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getXml(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getXml(id) was called
        Verify on resourcesUtil that resourcesUtil.getXml(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return xml calling getXml`() {
        val id = randomInt()
        val expectedResult: XmlResourceParser = mock()
        When calling baseResources.getXml(id) doReturn expectedResult

        val result = philologyResources.getXml(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getXml(id) was called
        Verify on resourcesUtil that resourcesUtil.getXml(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourceName`() {
        val id = randomInt()
        When calling baseResources.getResourceName(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getResourceName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourceName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourceName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Name calling getResourceName`() {
        val id = randomInt()
        val expectedResult = randomString()
        When calling baseResources.getResourceName(id) doReturn expectedResult

        val result = philologyResources.getResourceName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourceName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourceName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling parseBundleExtra`() {
        val tagName: String = randomString()
        val attrs: AttributeSet = mock()
        val outBundle: Bundle = mock()
        When calling baseResources.parseBundleExtra(tagName, attrs, outBundle) doThrow XmlPullParserException::class

        invoking { philologyResources.parseBundleExtra(tagName, attrs, outBundle) } `should throw` XmlPullParserException::class

        Verify on baseResources that baseResources.parseBundleExtra(tagName, attrs, outBundle) was called
        Verify on resourcesUtil that resourcesUtil.parseBundleExtra(tagName, attrs, outBundle) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Name calling parseBundleExtra`() {
        val tagName: String = randomString()
        val attrs: AttributeSet = mock()
        val outBundle: Bundle = mock()

        philologyResources.parseBundleExtra(tagName, attrs, outBundle)

        Verify on baseResources that baseResources.parseBundleExtra(tagName, attrs, outBundle) was called
        Verify on resourcesUtil that resourcesUtil.parseBundleExtra(tagName, attrs, outBundle) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDimensionPixelOffset`() {
        val id = randomInt()
        When calling baseResources.getDimensionPixelOffset(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getDimensionPixelOffset(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDimensionPixelOffset(id) was called
        Verify on resourcesUtil that resourcesUtil.getDimensionPixelOffset(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Integer calling getDimensionPixelOffset`() {
        val id = randomInt()
        val expectedResult = randomInt()
        When calling baseResources.getDimensionPixelOffset(id) doReturn expectedResult

        val result = philologyResources.getDimensionPixelOffset(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDimensionPixelOffset(id) was called
        Verify on resourcesUtil that resourcesUtil.getDimensionPixelOffset(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getValueForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val outValue: TypedValue = mock()
        val resolveRefs = randomBoolean()
        When calling baseResources.getValueForDensity(id, density, outValue, resolveRefs) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getValueForDensity(id, density, outValue, resolveRefs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getValueForDensity(id, density, outValue, resolveRefs) was called
        Verify on resourcesUtil that resourcesUtil.getValueForDensity(id, density, outValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Drawable calling getValueForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val outValue: TypedValue = mock()
        val resolveRefs = randomBoolean()

        philologyResources.getValueForDensity(id, density, outValue, resolveRefs)

        Verify on baseResources that baseResources.getValueForDensity(id, density, outValue, resolveRefs) was called
        Verify on resourcesUtil that resourcesUtil.getValueForDensity(id, density, outValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourceEntryName`() {
        val id = randomInt()
        When calling baseResources.getResourceEntryName(id) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getResourceEntryName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return Name calling getResourceEntryName`() {
        val id = randomInt()
        val expectedResult = randomString()
        When calling baseResources.getResourceEntryName(id) doReturn expectedResult

        val result = philologyResources.getResourceEntryName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on resourcesUtil that resourcesUtil.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getFraction`() {
        val id = randomInt()
        val base = randomInt()
        val pbase = randomInt()
        When calling baseResources.getFraction(id, base, pbase) doThrow Resources.NotFoundException::class

        invoking { philologyResources.getFraction(id, base, pbase) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getFraction(id, base, pbase) was called
        Verify on resourcesUtil that resourcesUtil.getFraction(id, base, pbase) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }

    @Test
    fun `Should return dimension calling getFraction`() {
        val id = randomInt()
        val base = randomInt()
        val pbase = randomInt()
        val expectedResult = randomFloat()
        When calling baseResources.getFraction(id, base, pbase) doReturn expectedResult

        val result = philologyResources.getFraction(id, base, pbase)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getFraction(id, base, pbase) was called
        Verify on resourcesUtil that resourcesUtil.getFraction(id, base, pbase) was called
        `Verify no further interactions` on baseResources
        `Verify no further interactions` on resourcesUtil
    }
}