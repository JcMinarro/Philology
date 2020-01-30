package com.jcminarro.philology

import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.Resources.Theme
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
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.When
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should throw`
import org.amshove.kluent.calling
import org.amshove.kluent.invoking
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import java.io.InputStream

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
    fun `Should return a defaultCharSequence asking for a text with a default value`() {
        val defaultCharSequence: CharSequence = "default char sequence"
        configureResourceGetIdException(baseResources, id)
        resources.getText(id, defaultCharSequence) `should be equal to` defaultCharSequence
    }

    @Test
    fun `Should return a CharSequence asking for a text with a default value`() {
        val defaultCharSequence: CharSequence = "default char sequence"
        configureResourceGetText(baseResources, id, nameId, someCharSequence)
        resources.getText(id, defaultCharSequence) `should be equal to` someCharSequence
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
    fun `Should throw an exception if the given id doesn't exist asking for a quantity text`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getQuantityText(id, 1) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for a quantity text`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        resources.getQuantityText(id, 1) `should be equal to` someCharSequence
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for an quantity string`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getQuantityString(id, 1) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for an quantity string`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        resources.getQuantityString(id, 1) `should be equal to` someString
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a string array`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getStringArray(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for a string array`() {
        val textArray = arrayOf<CharSequence>("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        resources.getStringArray(id) `should be equal to` textArray.map { it.toString() }.toTypedArray()
    }

    @Test
    fun `Should throw an exception if the given id doesn't exist asking for a text array`() {
        configureResourceGetIdException(baseResources, id)
        invoking { resources.getStringArray(id) } `should throw` Resources.NotFoundException::class
    }

    @Test
    fun `Should return a CharSequence asking for a text array`() {
        val textArray = arrayOf<CharSequence>("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        resources.getTextArray(id) `should be equal to` textArray
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
    fun `Should return a CharSequence from repository asking for a quantity text`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityText(id, 1) `should be equal to` repoCharSequence
    }

    @Test
    fun `Should return a CharSequence from repository asking for an quantity string`() {
        configureResourceQuantityString(baseResources, 1, "one")
        configureResourceGetQuantityText(baseResources, id, nameId, 1, someCharSequence)
        configurePhilology(createRepository(nameId, "one", repoCharSequence))
        resources.getQuantityString(id, 1) `should be equal to` repoString
    }

    @Test
    fun `Should return a CharSequence from repository asking for a string array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        configurePhilology(createRepository(nameId, textArray = textArray))
        resources.getStringArray(id) `should be equal to` textArray.map { it.toString() }.toTypedArray()
    }

    @Test
    fun `Should return a CharSequence from repository asking for a text array`() {
        val textArray: Array<CharSequence> = arrayOf("first", "second")
        configureResourceGetTextArray(baseResources, id, nameId, textArray)
        configurePhilology(createRepository(nameId, textArray = textArray))
        resources.getTextArray(id) `should be equal to` textArray
    }

    @Test
    fun `Should return an XmlResourceParser from base resources asking for an Animation`() {
        val xmlResourceParser = mock<XmlResourceParser>()
        When calling baseResources.getAnimation(id) doReturn xmlResourceParser
        resources.getAnimation(id) `should be equal to` xmlResourceParser
    }

    @Test
    fun `Should return a DisplayMetrics from base resources asking for a display metrics`() {
        val displayMetrics = mock<DisplayMetrics>()
        When calling baseResources.displayMetrics doReturn displayMetrics
        resources.displayMetrics `should be equal to` displayMetrics
    }

    @Test
    fun `Should return a Drawable from base resources asking for a drawable for density`() {
        val drawable = mock<Drawable>()
        When calling baseResources.getDrawableForDensity(id, 2) doReturn drawable
        resources.getDrawableForDensity(id, 2) `should be equal to` drawable
    }

    @Test
    fun `Should return a Drawable from base resources asking for a drawable for density on API 21+`() {
        val drawable = mock<Drawable>()
        val theme = mock<Theme>()
        When calling baseResources.getDrawableForDensity(id, 2, theme) doReturn drawable
        resources.getDrawableForDensity(id, 2, theme) `should be equal to` drawable
    }

    @Test
    fun `Should return Configuration from base resources asking for a configuration`() {
        val configuration = mock<Configuration>()
        When calling baseResources.configuration doReturn configuration
        resources.configuration `should be equal to` configuration
    }

    @Test
    fun `Should return TypedArray from base resources asking for a attributes`() {
        val attributes = mock<TypedArray>()
        val attributeSet = mock<AttributeSet>()
        val attrs = IntArray(3)
        When calling baseResources.obtainAttributes(attributeSet, attrs) doReturn attributes
        resources.obtainAttributes(attributeSet, attrs) `should be equal to` attributes
    }

    @Test
    fun `Should return Int from base resources asking for a dimension pixel size`() {
        When calling baseResources.getDimensionPixelSize(id) doReturn 15
        resources.getDimensionPixelSize(id) `should be equal to` 15
    }

    @Test
    fun `Should return IntArray from base resources asking for a int array`() {
        val intArray = IntArray(5)
        When calling baseResources.getIntArray(id) doReturn intArray
        resources.getIntArray(id) `should be equal to` intArray
    }

    @Test
    fun `Should call getValue method from base resources asking value by name`() {
        val outValue = mock<TypedValue>()
        resources.getValue(id, outValue, true)
        verify(baseResources).getValue(id, outValue, true)
    }

    @Test
    fun `Should call getValue method from base resources asking value by id`() {
        val outValue = mock<TypedValue>()
        resources.getValue("id", outValue, true)
        verify(baseResources).getValue("id", outValue, true)
    }

    @Test
    fun `Should return String from base resources asking for a resource package name`() {
        val packageName = "com.package.name"
        When calling baseResources.getResourcePackageName(id) doReturn packageName
        resources.getResourcePackageName(id) `should be equal to` packageName
    }

    @Test
    fun `Should return AssetFileDescriptor from base resources on open raw resource`() {
        val fileDescriptor = mock<AssetFileDescriptor>()
        When calling baseResources.openRawResourceFd(id) doReturn fileDescriptor
        resources.openRawResourceFd(id) `should be equal to` fileDescriptor
    }

    @Test
    fun `Should return Float from base resources asking for a dimension`() {
        val dimension = 15f
        When calling baseResources.getDimension(id) doReturn dimension
        resources.getDimension(id) `should be equal to` dimension
    }

    @Test
    fun `Should return ColorStateList from base resources asking for a color state list`() {
        val colorStateList = mock<ColorStateList>()
        When calling baseResources.getColorStateList(id) doReturn colorStateList
        resources.getColorStateList(id) `should be equal to` colorStateList
    }

    @Test
    fun `Should return ColorStateList from base resources asking for a color state list on API 23+`() {
        val colorStateList = mock<ColorStateList>()
        val theme = mock<Theme>()
        When calling baseResources.getColorStateList(id, theme) doReturn colorStateList
        resources.getColorStateList(id, theme) `should be equal to` colorStateList
    }

    @Test
    fun `Should return Boolean from base resources asking for a boolean`() {
        When calling baseResources.getBoolean(id) doReturn true
        resources.getBoolean(id) `should be equal to` true
    }

    @Test
    fun `Should return Int from base resources asking for a identifier`() {
        When calling baseResources.getIdentifier("name", "defType", "defPackage") doReturn 5
        resources.getIdentifier("name", "defType", "defPackage") `should be equal to` 5
    }

    @Test
    fun `Should return Int from base resources asking for a color`() {
        When calling baseResources.getColor(id) doReturn 15
        resources.getColor(id) `should be equal to` 15
    }

    @Test
    fun `Should return Int from base resources asking for a color on API 23+`() {
        val theme = mock<Theme>()
        When calling baseResources.getColor(id, theme) doReturn 15
        resources.getColor(id, theme) `should be equal to` 15
    }

    @Test
    fun `Should return InputStream from base resources on open raw resource`() {
        val inputStream = mock<InputStream>()
        When calling baseResources.openRawResource(id) doReturn inputStream
        resources.openRawResource(id) `should be equal to` inputStream
    }

    @Test
    fun `Should return InputStream from base resources on open raw resource with typed value param`() {
        val inputStream = mock<InputStream>()
        val typedValue = mock<TypedValue>()
        When calling baseResources.openRawResource(id, typedValue) doReturn inputStream
        resources.openRawResource(id, typedValue) `should be equal to` inputStream
    }

    @Test
    fun `Should return Movie from base resources asking for a movie`() {
        val movie = mock<Movie>()
        When calling baseResources.getMovie(id) doReturn movie
        resources.getMovie(id) `should be equal to` movie
    }

    @Test
    fun `Should return Integer from base resources asking for a integer`() {
        When calling baseResources.getInteger(id) doReturn 7
        resources.getInteger(id) `should be equal to` 7
    }

    @Test
    fun `Should call parseBundleExtras method from base resources on parse bundle extras`() {
        val parser = mock<XmlResourceParser>()
        val bundle = Bundle()
        resources.parseBundleExtras(parser, bundle)
        verify(baseResources).parseBundleExtras(parser, bundle)
    }

    @Test
    fun `Should return Drawable from base resources asking for a drawable`() {
        val drawable = mock<Drawable>()
        When calling baseResources.getDrawable(id) doReturn drawable
        resources.getDrawable(id) `should be equal to` drawable
    }

    @Test
    fun `Should return Drawable from base resources asking for a drawable on API 21+`() {
        val drawable = mock<Drawable>()
        val theme = mock<Theme>()
        When calling baseResources.getDrawable(id, theme) doReturn drawable
        resources.getDrawable(id, theme) `should be equal to` drawable
    }

    @Test
    fun `Should return String from base resources asking for a resource type name`() {
        val typeName = "typeName"
        When calling baseResources.getResourceTypeName(id) doReturn typeName
        resources.getResourceTypeName(id) `should be equal to` typeName
    }

    @Test
    fun `Should return XmlResourceParser from base resources asking for a layout`() {
        val xmlParser = mock<XmlResourceParser>()
        When calling baseResources.getLayout(id) doReturn xmlParser
        resources.getLayout(id) `should be equal to` xmlParser
    }

    @Test
    fun `Should return Typeface from base resources asking for a font`() {
        val typeface = mock<Typeface>()
        When calling baseResources.getFont(id) doReturn typeface
        resources.getFont(id) `should be equal to` typeface
    }

    @Test
    fun `Should return XmlResourceParser from base resources asking for a xml`() {
        val xmlParser = mock<XmlResourceParser>()
        When calling baseResources.getXml(id) doReturn xmlParser
        resources.getXml(id) `should be equal to` xmlParser
    }

    @Test
    fun `Should return String from base resources asking for a resource name`() {
        val resourceName = "resourceName"
        When calling baseResources.getResourceName(id) doReturn resourceName
        resources.getResourceName(id) `should be equal to` resourceName
    }

    @Test
    fun `Should call parseBundleExtra method from base resources on parse bundle extra`() {
        val tagName = "tagName"
        val attrs = mock<AttributeSet>()
        val bundle = Bundle()
        resources.parseBundleExtra(tagName, attrs, bundle)
        verify(baseResources).parseBundleExtra(tagName, attrs, bundle)
    }

    @Test
    fun `Should return Int from base resources asking for a dimension pixel offset`() {
        When calling baseResources.getDimensionPixelOffset(id) doReturn 15
        resources.getDimensionPixelOffset(id) `should be equal to` 15
    }

    @Test
    fun `Should call getValueForDensity method from base resources on get value for density`() {
        val outValue = mock<TypedValue>()
        resources.getValueForDensity(id, 2, outValue, true)
        verify(baseResources).getValueForDensity(id, 2, outValue, true)
    }

    @Test
    fun `Should return String from base resources asking for a resource entry name`() {
        val entryName = "entryName"
        When calling baseResources.getResourceEntryName(id) doReturn entryName
        resources.getResourceEntryName(id) `should be equal to` entryName
    }

    @Test
    fun `Should return Float from base resources asking for a fraction`() {
        val fraction = 132f
        When calling baseResources.getFraction(id, 2, 5) doReturn fraction
        resources.getFraction(id, 2, 5) `should be equal to` fraction
    }
}
