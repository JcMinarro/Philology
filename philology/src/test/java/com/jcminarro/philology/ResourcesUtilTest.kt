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
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class ResourcesUtilTest {

    private val configuration: Configuration = createConfiguration()
    private val baseResources: Resources = mock()
    private val resourcesUtil = ResourcesUtil(baseResources)
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

        invoking { resourcesUtil.getText(id) } `should throw` Resources.NotFoundException::class

	    Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)

        val result = resourcesUtil.getText(id)

        result `should be equal to` someCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))

        val result = resourcesUtil.getText(id)

        result `should be equal to` repoCharSequence
	    Verify on baseResources that baseResources.configuration was called
	    Verify on baseResources that baseResources.getResourceEntryName(id) was called
	    `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a text with default value`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)

        val result = resourcesUtil.getText(id, randomString())

        result `should be equal to` someCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text with default value`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))

        val result = resourcesUtil.getText(id, randomString())

        result `should be equal to` repoCharSequence
	    Verify on baseResources that baseResources.configuration was called
	    Verify on baseResources that baseResources.getResourceEntryName(id) was called
	    `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a the default CharSequence asking for a text`() {
        val defaultCharSequence: CharSequence = randomString()
        configureResourceGetIdException(baseResources, id)

        val result = resourcesUtil.getText(id, defaultCharSequence)

        result `should be equal to` defaultCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
	    `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an String`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getString(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)

        val result = resourcesUtil.getString(id)

        result `should be equal to` someString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence from repository asking for an String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, repoCharSequence))

        val result = resourcesUtil.getString(id)

        result `should be equal to` repoString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a formatted String`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getString(id, formatArg) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a formatted String`() {
        configureResourceGetText(baseResources, id, nameId, "$someCharSequence%s")

        val result = resourcesUtil.getString(id, formatArg)

        result `should be equal to` "$someString$formatArg"
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getText(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence from repository asking for a formatted String`() {
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        configurePhilology(createRepository(nameId, null, "$repoCharSequence%s"))

        val result = resourcesUtil.getString(id, formatArg)

        result `should be equal to` "$repoString$formatArg"
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a quantity text`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getQuantityText(id, randomInt()) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text`() {
        val quantity = randomInt()
        configureResourceQuantityString(baseResources, quantity, randomString())
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)

        val result = resourcesUtil.getQuantityText(id, quantity)

        result `should be equal to` someCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on baseResources that baseResources.getQuantityText(id, quantity) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text from repository`() {
        val quantity = randomInt()
        val quantityString = randomString()
        configureResourceQuantityString(baseResources, quantity, quantityString)
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, quantityString, repoCharSequence))

        val result = resourcesUtil.getQuantityText(id, quantity)

        result `should be equal to` repoCharSequence
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an quantity String`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getQuantityString(id, randomInt()) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for an quantity String`() {
        val quantity = randomInt()
        configureResourceQuantityString(baseResources, quantity, randomString())
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)

        val result = resourcesUtil.getQuantityString(id, quantity)

        result `should be equal to` someString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on baseResources that baseResources.getQuantityText(id, quantity) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for an quantity String from repository`() {
        val quantity = randomInt()
        val quantityString = randomString()
        configureResourceQuantityString(baseResources, quantity, quantityString)
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, quantityString, repoCharSequence))

        val result = resourcesUtil.getQuantityString(id, quantity)

        result `should be equal to` repoString
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if id doesn't exist asking for a formatted quantity String`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getQuantityString(id, randomInt(), formatArg) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a formatted quantity String`() {
        val quantity = randomInt()
        configureResourceQuantityString(baseResources, quantity, randomString())
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, "$someCharSequence%s")

        val result = resourcesUtil.getQuantityString(id, quantity, formatArg)

        result `should be equal to` someString + formatArg
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        Verify on baseResources that baseResources.getQuantityText(id, quantity) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return a CharSequence asking for a formatted quantity text from repository`() {
        val quantity = randomInt()
        val quantityString = randomString()
        configureResourceQuantityString(baseResources, quantity, quantityString)
        configureResourceGetQuantityText(baseResources, id, nameId, quantity, someCharSequence)
        configurePhilology(createRepository(nameId, quantityString, "$repoCharSequence%s"))

        val result = resourcesUtil.getQuantityString(id, quantity, formatArg)

        result `should be equal to` "$repoCharSequence$formatArg"
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, quantity)
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for text array`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getTextArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return an array of strings asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        configureResourceGetTextArray(baseResources, id, nameId, textArray)

        val result = resourcesUtil.getTextArray(id)

        result `should be equal to` textArray
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getTextArray(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return an array of strings from repository asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        configureResourceGetTextArray(baseResources, id, nameId, arrayOf(randomString(), randomString()))
        configurePhilology(createRepository(nameId, textArray = textArray))

        val result = resourcesUtil.getTextArray(id)

        result `should be equal to` textArray
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a string array`() {
        configureResourceGetIdException(baseResources, id)

        invoking { resourcesUtil.getStringArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return an array of strings asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        val expectedResult = textArray.map { it.toString() }.toTypedArray()
        configureResourceGetTextArray(baseResources, id, nameId, textArray)

        val result = resourcesUtil.getStringArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        Verify on baseResources that baseResources.getTextArray(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return an array of strings from repository asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf(randomString(), randomString())
        val expectedResult = textArray.map { it.toString() }.toTypedArray()
        configureResourceGetTextArray(baseResources, id, nameId, arrayOf(randomString(), randomString()))
        configurePhilology(createRepository(nameId, textArray = textArray))

        val result = resourcesUtil.getStringArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.configuration was called
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getAnimation`() {
        val id = randomInt()
        When calling baseResources.getAnimation(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getAnimation(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getAnimation(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return animation calling getAnimation`() {
        val id = randomInt()
        val expectedResult: XmlResourceParser = mock()
        When calling baseResources.getAnimation(id) doReturn expectedResult

        val result = resourcesUtil.getAnimation(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getAnimation(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return displayMetrics calling getDisplayMetrics`() {
        val expectedResult: DisplayMetrics = mock()
        When calling baseResources.displayMetrics doReturn expectedResult

        val result = resourcesUtil.getDisplayMetrics()

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.displayMetrics was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        When calling baseResources.getDrawableForDensity(id, density) doThrow Resources
                .NotFoundException::class

        invoking { resourcesUtil.getDrawableForDensity(id, density) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getDrawableForDensity(id, density) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Drawable calling getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawableForDensity(id, density) doReturn expectedResult

        val result = resourcesUtil.getDrawableForDensity(id, density)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawableForDensity(id, density) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling  getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getDrawableForDensity(id, density, theme) doThrow Resources
                .NotFoundException::class

        invoking { resourcesUtil.getDrawableForDensity(id, density, theme) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getDrawableForDensity(id, density, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Drawable calling  getDrawableForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawableForDensity(id, density, theme) doReturn expectedResult

        val result = resourcesUtil.getDrawableForDensity(id, density, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawableForDensity(id, density, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return configuration calling getConfiguration`() {
        val result = resourcesUtil.getConfiguration()

        result `should be equal to` configuration
        Verify on baseResources that baseResources.configuration was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception calling obtainAttributes`() {
        val set: AttributeSet = mock()
        val attrs: IntArray = intArrayOf(randomInt())
        When calling baseResources.obtainAttributes(set, attrs) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.obtainAttributes(set, attrs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.obtainAttributes(set, attrs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return animation calling obtainAttributes`() {
        val set: AttributeSet = mock()
        val attrs: IntArray = intArrayOf(randomInt())
        val expectedResult: TypedArray = mock()
        When calling baseResources.obtainAttributes(set, attrs) doReturn expectedResult

        val result = resourcesUtil.obtainAttributes(set, attrs)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.obtainAttributes(set, attrs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling obtainTypedArray`() {
        val id = randomInt()
        When calling baseResources.obtainTypedArray(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.obtainTypedArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.obtainTypedArray(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return TypedArray calling obtainTypedArray`() {
        val id = randomInt()
        val expectedResult: TypedArray = mock()
        When calling baseResources.obtainTypedArray(id) doReturn expectedResult

        val result = resourcesUtil.obtainTypedArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.obtainTypedArray(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDimensionPixelSize`() {
        val id = randomInt()
        When calling baseResources.getDimensionPixelSize(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getDimensionPixelSize(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDimensionPixelSize(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return dimension calling getDimensionPixelSize`() {
        val id = randomInt()
        val expectedResult: Int = randomInt()
        When calling baseResources.getDimensionPixelSize(id) doReturn expectedResult

        val result = resourcesUtil.getDimensionPixelSize(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDimensionPixelSize(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getIntArray`() {
        val id = randomInt()
        When calling baseResources.getIntArray(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getIntArray(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getIntArray(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return intArray calling getIntArray`() {
        val id = randomInt()
        val expectedResult: IntArray = intArrayOf(randomInt(), randomInt(), randomInt())
        When calling baseResources.getIntArray(id) doReturn expectedResult

        val result = resourcesUtil.getIntArray(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getIntArray(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getValue`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()
        When calling baseResources.getValue(id, typedValue, resolveRefs) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getValue(id, typedValue, resolveRefs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getValue(id, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return value calling getValue`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()

        resourcesUtil.getValue(id, typedValue, resolveRefs)

        Verify on baseResources that baseResources.getValue(id, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given name doesn't exist calling getValue`() {
        val name = randomString()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()
        When calling baseResources.getValue(name, typedValue, resolveRefs) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getValue(name, typedValue, resolveRefs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getValue(name, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return value  calling getValue`() {
        val name = randomString()
        val typedValue: TypedValue = mock()
        val resolveRefs = randomBoolean()

        resourcesUtil.getValue(name, typedValue, resolveRefs)

        Verify on baseResources that baseResources.getValue(name, typedValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourcePackageName`() {
        val id = randomInt()
        When calling baseResources.getResourcePackageName(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getResourcePackageName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourcePackageName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return package calling getResourcePackageName`() {
        val id = randomInt()
        val expectedResult: String = randomString()
        When calling baseResources.getResourcePackageName(id) doReturn expectedResult

        val result = resourcesUtil.getResourcePackageName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourcePackageName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling openRawResourceFd`() {
        val id = randomInt()
        When calling baseResources.openRawResourceFd(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.openRawResourceFd(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.openRawResourceFd(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return assetFileDescriptor calling openRawResourceFd`() {
        val id = randomInt()
        val expectedResult: AssetFileDescriptor = mock()
        When calling baseResources.openRawResourceFd(id) doReturn expectedResult

        val result = resourcesUtil.openRawResourceFd(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.openRawResourceFd(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDimension`() {
        val id = randomInt()
        When calling baseResources.getDimension(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getDimension(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDimension(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return dimension calling getDimension`() {
        val id = randomInt()
        val expectedResult = randomFloat()
        When calling baseResources.getDimension(id) doReturn expectedResult

        val result = resourcesUtil.getDimension(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDimension(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getColorStateList`() {
        val id = randomInt()
        When calling baseResources.getColorStateList(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getColorStateList(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getColorStateList(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return colorStateList calling getColorStateList`() {
        val id = randomInt()
        val expectedResult: ColorStateList = mock()
        When calling baseResources.getColorStateList(id) doReturn expectedResult

        val result = resourcesUtil.getColorStateList(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColorStateList(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling getColorStateList`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getColorStateList(id, theme) doThrow Resources
                .NotFoundException::class

        invoking { resourcesUtil.getColorStateList(id, theme) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getColorStateList(id, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return colorStateList  calling getColorStateList`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult: ColorStateList = mock()
        When calling baseResources.getColorStateList(id, theme) doReturn expectedResult

        val result = resourcesUtil.getColorStateList(id, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColorStateList(id, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getBoolean`() {
        val id = randomInt()
        When calling baseResources.getBoolean(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getBoolean(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getBoolean(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return boolean calling getBoolean`() {
        val id = randomInt()
        val expectedResult = randomBoolean()
        When calling baseResources.getBoolean(id) doReturn expectedResult

        val result = resourcesUtil.getBoolean(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getBoolean(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getIdentifier`() {
        val name = randomString()
        val defType = randomString()
        val defPackage = randomString()
        When calling baseResources.getIdentifier(name, defType, defPackage) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getIdentifier(name, defType, defPackage) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getIdentifier(name, defType, defPackage) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return integer calling getIdentifier`() {
        val name = randomString()
        val defType = randomString()
        val defPackage = randomString()
        val expectedResult = randomInt()
        When calling baseResources.getIdentifier(name, defType, defPackage) doReturn expectedResult

        val result = resourcesUtil.getIdentifier(name, defType, defPackage)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getIdentifier(name, defType, defPackage) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getColor`() {
        val id = randomInt()
        When calling baseResources.getColor(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getColor(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getColor(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return color calling getColor`() {
        val id = randomInt()
        val expectedResult = randomInt()
        When calling baseResources.getColor(id) doReturn expectedResult

        val result = resourcesUtil.getColor(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColor(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling getColor`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getColor(id, theme) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getColor(id, theme) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getColor(id, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return color  calling getColor`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult = randomInt()
        When calling baseResources.getColor(id, theme) doReturn expectedResult

        val result = resourcesUtil.getColor(id, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getColor(id, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should call to updateConfiguration`() {
        val newConf: Configuration = mock()
        val newMetrics: DisplayMetrics = mock()

        resourcesUtil.updateConfiguration(newConf, newMetrics)

        Verify on baseResources that baseResources.updateConfiguration(newConf, newMetrics) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling openRawResource`() {
        val id = randomInt()
        When calling baseResources.openRawResource(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.openRawResource(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.openRawResource(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return inputStream calling openRawResource`() {
        val id = randomInt()
        val expectedResult: InputStream = mock()
        When calling baseResources.openRawResource(id) doReturn expectedResult

        val result = resourcesUtil.openRawResource(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.openRawResource(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling openRawResource`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        When calling baseResources.openRawResource(id, typedValue) doThrow Resources
                .NotFoundException::class

        invoking { resourcesUtil.openRawResource(id, typedValue) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.openRawResource(id, typedValue) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return inputStream  calling openRawResource`() {
        val id = randomInt()
        val typedValue: TypedValue = mock()
        val expectedResult: InputStream = mock()
        When calling baseResources.openRawResource(id, typedValue) doReturn expectedResult

        val result = resourcesUtil.openRawResource(id, typedValue)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.openRawResource(id, typedValue) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getMovie`() {
        val id = randomInt()
        When calling baseResources.getMovie(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getMovie(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getMovie(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Movie calling getMovie`() {
        val id = randomInt()
        val expectedResult: Movie = mock()
        When calling baseResources.getMovie(id) doReturn expectedResult

        val result = resourcesUtil.getMovie(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getMovie(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getInteger`() {
        val id = randomInt()
        When calling baseResources.getInteger(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getInteger(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getInteger(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Integer calling getInteger`() {
        val id = randomInt()
        val expectedResult = randomInt()
        When calling baseResources.getInteger(id) doReturn expectedResult

        val result = resourcesUtil.getInteger(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getInteger(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an XmlPullParserException if the given id doesn't exist calling parseBundleExtras`() {
        val parser: XmlResourceParser = mock()
        val outBundle: Bundle = mock()
        When calling baseResources.parseBundleExtras(parser, outBundle) doThrow XmlPullParserException::class

        invoking { resourcesUtil.parseBundleExtras(parser, outBundle) } `should throw` XmlPullParserException::class

        Verify on baseResources that baseResources.parseBundleExtras(parser, outBundle) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an IOException if the given id doesn't exist calling parseBundleExtras`() {
        val parser: XmlResourceParser = mock()
        val outBundle: Bundle = mock()
        When calling baseResources.parseBundleExtras(parser, outBundle) doThrow IOException::class

        invoking { resourcesUtil.parseBundleExtras(parser, outBundle) } `should throw` IOException::class

        Verify on baseResources that baseResources.parseBundleExtras(parser, outBundle) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Integer calling parseBundleExtras`() {
        val parser: XmlResourceParser = mock()
        val outBundle: Bundle = mock()

        resourcesUtil.parseBundleExtras(parser, outBundle)

        Verify on baseResources that baseResources.parseBundleExtras(parser, outBundle) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDrawable`() {
        val id = randomInt()
        When calling baseResources.getDrawable(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getDrawable(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDrawable(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Drawable calling getDrawable`() {
        val id = randomInt()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawable(id) doReturn expectedResult

        val result = resourcesUtil.getDrawable(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawable(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist  calling getDrawable`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        When calling baseResources.getDrawable(id, theme) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getDrawable(id, theme) } `should throw` Resources
                .NotFoundException::class

        Verify on baseResources that baseResources.getDrawable(id, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Drawable  calling getDrawable`() {
        val id = randomInt()
        val theme: Resources.Theme = mock()
        val expectedResult: Drawable = mock()
        When calling baseResources.getDrawable(id, theme) doReturn expectedResult

        val result = resourcesUtil.getDrawable(id, theme)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDrawable(id, theme) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourceTypeName`() {
        val id = randomInt()
        When calling baseResources.getResourceTypeName(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getResourceTypeName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourceTypeName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Name calling getResourceTypeName`() {
        val id = randomInt()
        val expectedResult = randomString()
        When calling baseResources.getResourceTypeName(id) doReturn expectedResult

        val result = resourcesUtil.getResourceTypeName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourceTypeName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getLayout`() {
        val id = randomInt()
        When calling baseResources.getLayout(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getLayout(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getLayout(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return layout calling getLayout`() {
        val id = randomInt()
        val expectedResult: XmlResourceParser = mock()
        When calling baseResources.getLayout(id) doReturn expectedResult

        val result = resourcesUtil.getLayout(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getLayout(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getFont`() {
        val id = randomInt()
        When calling baseResources.getFont(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getFont(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getFont(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return typeFace calling getFont`() {
        val id = randomInt()
        val expectedResult: Typeface = mock()
        When calling baseResources.getFont(id) doReturn expectedResult

        val result = resourcesUtil.getFont(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getFont(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getXml`() {
        val id = randomInt()
        When calling baseResources.getXml(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getXml(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getXml(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return xml calling getXml`() {
        val id = randomInt()
        val expectedResult: XmlResourceParser = mock()
        When calling baseResources.getXml(id) doReturn expectedResult

        val result = resourcesUtil.getXml(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getXml(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourceName`() {
        val id = randomInt()
        When calling baseResources.getResourceName(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getResourceName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourceName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Name calling getResourceName`() {
        val id = randomInt()
        val expectedResult = randomString()
        When calling baseResources.getResourceName(id) doReturn expectedResult

        val result = resourcesUtil.getResourceName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourceName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling parseBundleExtra`() {
        val tagName: String = randomString()
        val attrs: AttributeSet = mock()
        val outBundle: Bundle = mock()
        When calling baseResources.parseBundleExtra(tagName, attrs, outBundle) doThrow XmlPullParserException::class

        invoking { resourcesUtil.parseBundleExtra(tagName, attrs, outBundle) } `should throw` XmlPullParserException::class

        Verify on baseResources that baseResources.parseBundleExtra(tagName, attrs, outBundle) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Name calling parseBundleExtra`() {
        val tagName: String = randomString()
        val attrs: AttributeSet = mock()
        val outBundle: Bundle = mock()

        resourcesUtil.parseBundleExtra(tagName, attrs, outBundle)

        Verify on baseResources that baseResources.parseBundleExtra(tagName, attrs, outBundle) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getDimensionPixelOffset`() {
        val id = randomInt()
        When calling baseResources.getDimensionPixelOffset(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getDimensionPixelOffset(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getDimensionPixelOffset(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Integer calling getDimensionPixelOffset`() {
        val id = randomInt()
        val expectedResult = randomInt()
        When calling baseResources.getDimensionPixelOffset(id) doReturn expectedResult

        val result = resourcesUtil.getDimensionPixelOffset(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getDimensionPixelOffset(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getValueForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val outValue: TypedValue = mock()
        val resolveRefs = randomBoolean()
        When calling baseResources.getValueForDensity(id, density, outValue, resolveRefs) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getValueForDensity(id, density, outValue, resolveRefs) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getValueForDensity(id, density, outValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Drawable calling getValueForDensity`() {
        val id = randomInt()
        val density = randomInt()
        val outValue: TypedValue = mock()
        val resolveRefs = randomBoolean()

        resourcesUtil.getValueForDensity(id, density, outValue, resolveRefs)

        Verify on baseResources that baseResources.getValueForDensity(id, density, outValue, resolveRefs) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getResourceEntryName`() {
        val id = randomInt()
        When calling baseResources.getResourceEntryName(id) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getResourceEntryName(id) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return Name calling getResourceEntryName`() {
        val id = randomInt()
        val expectedResult = randomString()
        When calling baseResources.getResourceEntryName(id) doReturn expectedResult

        val result = resourcesUtil.getResourceEntryName(id)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getResourceEntryName(id) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist calling getFraction`() {
        val id = randomInt()
        val base = randomInt()
        val pbase = randomInt()
        When calling baseResources.getFraction(id, base, pbase) doThrow Resources.NotFoundException::class

        invoking { resourcesUtil.getFraction(id, base, pbase) } `should throw` Resources.NotFoundException::class

        Verify on baseResources that baseResources.getFraction(id, base, pbase) was called
        `Verify no further interactions` on baseResources
    }

    @Test
    fun `Should return dimension calling getFraction`() {
        val id = randomInt()
        val base = randomInt()
        val pbase = randomInt()
        val expectedResult = randomFloat()
        When calling baseResources.getFraction(id, base, pbase) doReturn expectedResult

        val result = resourcesUtil.getFraction(id, base, pbase)

        result `should be equal to` expectedResult
        Verify on baseResources that baseResources.getFraction(id, base, pbase) was called
        `Verify no further interactions` on baseResources
    }
}
