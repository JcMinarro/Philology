package com.jcminarro.philology

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.icu.text.PluralRules
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.AnyRes
import androidx.annotation.ArrayRes
import androidx.annotation.BoolRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.FractionRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.PluralsRes
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.annotation.StyleableRes
import androidx.annotation.XmlRes
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.util.Locale

internal class ResourcesUtil(internal val baseResources: Resources) {
    private val locale: Locale by lazy { baseResources.currentLocale() }
    private val repository: PhilologyRepository by lazy {
        Philology.getPhilologyRepository(locale)
    }

    @Throws(NotFoundException::class)
    fun getText(@StringRes id: Int): CharSequence = getTextFromId(id)

	fun getText(@StringRes id: Int, def: CharSequence): CharSequence = try {
		getTextFromId(id)
    } catch (_: NotFoundException) {
        def
    }

    @Throws(NotFoundException::class)
    fun getString(@StringRes id: Int): String = getTextFromId(id).toString()

    @Throws(NotFoundException::class)
    fun getString(@StringRes id: Int, vararg formatArgs: Any?): String =
            getFormattedText(getTextFromId(id).toString(), *formatArgs)

    @Throws(NotFoundException::class)
    fun getQuantityText(@PluralsRes id: Int, quantity: Int): CharSequence = getQuantityTextFromId(id, quantity)

    @Throws(NotFoundException::class)
    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String = getQuantityTextFromId(id, quantity).toString()

    @Throws(NotFoundException::class)
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any?): String =
        getFormattedText(getQuantityTextFromId(id, quantity).toString(), *formatArgs)

    @Throws(NotFoundException::class)
    fun getTextArray(@ArrayRes id: Int): Array<CharSequence> = getTextArrayFromId(id)

    @Throws(NotFoundException::class)
    fun getStringArray(@ArrayRes id: Int): Array<String> =
            getTextArrayFromId(id).map { it.toString() }.toTypedArray()

    @Throws(NotFoundException::class)
    fun getAnimation(@AnimatorRes @AnimRes id: Int): XmlResourceParser = baseResources.getAnimation(id)

    fun getDisplayMetrics(): DisplayMetrics = baseResources.displayMetrics

    @Throws(NotFoundException::class)
    fun getDrawableForDensity(@DrawableRes id: Int, density: Int): Drawable? =
            baseResources.getDrawableForDensity(id, density)

    @Throws(NotFoundException::class)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun getDrawableForDensity(@DrawableRes id: Int, density: Int, theme: Resources.Theme?): Drawable? =
            baseResources.getDrawableForDensity(id, density, theme)

    fun getConfiguration(): Configuration = baseResources.configuration

    fun obtainAttributes(set: AttributeSet?, @StyleableRes attrs: IntArray?): TypedArray =
            baseResources.obtainAttributes(set, attrs)

    @Throws(NotFoundException::class)
    fun obtainTypedArray(@ArrayRes id: Int): TypedArray = baseResources.obtainTypedArray(id)

    @Throws(NotFoundException::class)
    fun getDimensionPixelSize(@DimenRes id: Int): Int = baseResources.getDimensionPixelSize(id)

    @Throws(NotFoundException::class)
    fun getIntArray(@ArrayRes id: Int): IntArray = baseResources.getIntArray(id)

    @Throws(NotFoundException::class)
    fun getValue(@AnyRes id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        baseResources.getValue(id, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
        baseResources.getValue(name, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    fun getResourcePackageName(@AnyRes resid: Int): String =
            baseResources.getResourcePackageName(resid)

    @Throws(NotFoundException::class)
    fun openRawResourceFd(@RawRes id: Int): AssetFileDescriptor =
            baseResources.openRawResourceFd(id)

    @Throws(NotFoundException::class)
    fun getDimension(@DimenRes id: Int): Float = baseResources.getDimension(id)

    @Throws(NotFoundException::class)
    fun getColorStateList(@ColorRes id: Int): ColorStateList = baseResources.getColorStateList(id)

    @TargetApi(Build.VERSION_CODES.M)
    fun getColorStateList(@ColorRes id: Int, theme: Resources.Theme?): ColorStateList =
            baseResources.getColorStateList(id, theme)

    @Throws(NotFoundException::class)
    fun getBoolean(@BoolRes id: Int): Boolean = baseResources.getBoolean(id)

    fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int =
            baseResources.getIdentifier(name, defType, defPackage)

    @ColorInt
    @Throws(NotFoundException::class)
    fun getColor(@ColorRes id: Int): Int = baseResources.getColor(id)

    @TargetApi(Build.VERSION_CODES.M)
    @ColorInt
    @Throws(NotFoundException::class)
    fun getColor(@ColorRes id: Int, theme: Resources.Theme?): Int = baseResources.getColor(id, theme)

    fun updateConfiguration(config: Configuration?, metrics: DisplayMetrics?) {
        baseResources.updateConfiguration(config, metrics)
    }

    @Throws(NotFoundException::class)
    fun openRawResource(@RawRes id: Int): InputStream = baseResources.openRawResource(id)

    @Throws(NotFoundException::class)
    fun openRawResource(@RawRes id: Int, value: TypedValue?): InputStream =
            baseResources.openRawResource(id, value)

    @Throws(NotFoundException::class)
    fun getMovie(@RawRes id: Int): Movie = baseResources.getMovie(id)

    @Throws(NotFoundException::class)
    fun getInteger(@IntegerRes id: Int): Int = baseResources.getInteger(id)

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseBundleExtras(parser: XmlResourceParser?, outBundle: Bundle?) {
        this.baseResources.parseBundleExtras(parser, outBundle)
    }

    @Throws(NotFoundException::class)
    fun getDrawable(@DrawableRes id: Int): Drawable = baseResources.getDrawable(id)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Throws(NotFoundException::class)
    fun getDrawable(@DrawableRes id: Int, theme: Resources.Theme?): Drawable =
            baseResources.getDrawable(id, theme)

    @Throws(NotFoundException::class)
    fun getResourceTypeName(@AnyRes resid: Int): String = baseResources.getResourceTypeName(resid)

    @Throws(NotFoundException::class)
    fun getLayout(@LayoutRes id: Int): XmlResourceParser = baseResources.getLayout(id)

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    @Throws(NotFoundException::class)
    fun getFont(@FontRes id: Int): Typeface = baseResources.getFont(id)

    @Throws(NotFoundException::class)
    fun getXml(@XmlRes id: Int): XmlResourceParser = baseResources.getXml(id)

    @Throws(NotFoundException::class)
    fun getResourceName(@AnyRes resid: Int): String = baseResources.getResourceName(resid)

    @Throws(XmlPullParserException::class)
    fun parseBundleExtra(tagName: String?, attrs: AttributeSet?, outBundle: Bundle?) {
        baseResources.parseBundleExtra(tagName, attrs, outBundle)
    }

    @Throws(NotFoundException::class)
    fun getDimensionPixelOffset(@DimenRes id: Int): Int = baseResources.getDimensionPixelOffset(id)

    @Throws(NotFoundException::class)
    fun getValueForDensity( @AnyRes id: Int, density: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        baseResources.getValueForDensity(id, density, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    fun getResourceEntryName(@AnyRes resid: Int): String = baseResources.getResourceEntryName(resid)

    @Throws(NotFoundException::class)
    fun getFraction(@FractionRes id: Int, base: Int, pbase: Int): Float =
            baseResources.getFraction(id, base, pbase)

    private fun getFormattedText(format: String, vararg formatArgs: Any?) =
            String.format(locale, format, *formatArgs)

	private fun getTextFromId(id: Int) =
			(repository.getText(baseResources.getResourceEntryName(id))
					?: baseResources.getText(id))

    private fun getQuantityTextFromId(id: Int, quantity: Int): CharSequence = repository.getPlural(
            baseResources.getResourceEntryName(id),
            quantity.toPluralKeyword(baseResources, locale)
    ) ?: baseResources.getQuantityText(id, quantity)

    private fun getTextArrayFromId(id: Int): Array<CharSequence> =
            repository.getTextArray(baseResources.getResourceEntryName(id))
                    ?: baseResources.getTextArray(id)
}

interface PhilologyRepository {
    fun getText(key: String): CharSequence? = null
    fun getPlural(key: String, quantityString: String): CharSequence? = null
    fun getTextArray(key: String): Array<CharSequence>? = null
}

@SuppressWarnings("NewApi")
private fun Resources.currentLocale(): Locale = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
    @Suppress("DEPRECATION")
    configuration.locale
} else {
    configuration.locales[0]
}

private fun Int.toPluralKeyword(baseResources: Resources, locale: Locale): String =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PluralRules.forLocale(locale).select(this.toDouble())
        } else {
            baseResources.getQuantityString(R.plurals.com_jcminarro_philology_quantity_string, this)
        }
