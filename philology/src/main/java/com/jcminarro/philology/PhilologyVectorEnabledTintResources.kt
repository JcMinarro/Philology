package com.jcminarro.philology

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.TypedArray
import android.content.res.XmlResourceParser
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
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
import androidx.appcompat.widget.VectorEnabledTintResources
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

@SuppressWarnings("RestrictedApi")
@SuppressLint("RestrictedApi")
internal class PhilologyVectorEnabledTintResources(baseContext: Context,
                                                   private val resourcesUtil: ResourcesUtil
) : VectorEnabledTintResources(baseContext, resourcesUtil.baseResources) {

    @Throws(NotFoundException::class)
    override fun getText(@StringRes id: Int): CharSequence = resourcesUtil.getText(id)

    override fun getText(@StringRes id: Int, def: CharSequence): CharSequence = resourcesUtil.getText(id, def)

    @Throws(NotFoundException::class)
    override fun getString(@StringRes id: Int): String = resourcesUtil.getString(id)

    @Throws(NotFoundException::class)
    override fun getString(@StringRes id: Int, vararg formatArgs: Any?): String =
            resourcesUtil.getString(id, *formatArgs)

    @Throws(NotFoundException::class)
    override fun getQuantityText(@PluralsRes id: Int, quantity: Int): CharSequence =
            resourcesUtil.getQuantityText(id, quantity)

    @Throws(NotFoundException::class)
    override fun getQuantityString(@PluralsRes id: Int, quantity: Int): String =
            resourcesUtil.getQuantityString(id, quantity)

    @Throws(NotFoundException::class)
    override fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any?): String =
            resourcesUtil.getQuantityString(id, quantity, *formatArgs)

    @Throws(NotFoundException::class)
    override fun getTextArray(@ArrayRes id: Int): Array<CharSequence> = resourcesUtil.getTextArray(id)

    @Throws(NotFoundException::class)
    override fun getStringArray(@ArrayRes id: Int): Array<String> = resourcesUtil.getStringArray(id)

    @Throws(NotFoundException::class)
    override fun getAnimation(@AnimatorRes @AnimRes id: Int): XmlResourceParser = resourcesUtil.getAnimation(id)

    override fun getDisplayMetrics(): DisplayMetrics = resourcesUtil.getDisplayMetrics()

    @Throws(NotFoundException::class)
    override fun getDrawableForDensity(@DrawableRes id: Int, density: Int): Drawable? =
            resourcesUtil.getDrawableForDensity(id, density)

    @Throws(NotFoundException::class)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getDrawableForDensity(@DrawableRes id: Int, density: Int, theme: Theme?): Drawable? =
            resourcesUtil.getDrawableForDensity(id, density, theme)

    override fun getConfiguration(): Configuration = resourcesUtil.getConfiguration()

    override fun obtainAttributes(set: AttributeSet?, @StyleableRes attrs: IntArray?): TypedArray =
            resourcesUtil.obtainAttributes(set, attrs)

    @Throws(NotFoundException::class)
    override fun obtainTypedArray(@ArrayRes id: Int): TypedArray = resourcesUtil.obtainTypedArray(id)

    @Throws(NotFoundException::class)
    override fun getDimensionPixelSize(@DimenRes id: Int): Int = resourcesUtil.getDimensionPixelSize(id)

    @Throws(NotFoundException::class)
    override fun getIntArray(@ArrayRes id: Int): IntArray = resourcesUtil.getIntArray(id)

    @Throws(NotFoundException::class)
    override fun getValue(@AnyRes id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        resourcesUtil.getValue(id, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    override fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
        resourcesUtil.getValue(name, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    override fun getResourcePackageName(@AnyRes resid: Int): String =
            resourcesUtil.getResourcePackageName(resid)

    @Throws(NotFoundException::class)
    override fun openRawResourceFd(@RawRes id: Int): AssetFileDescriptor =
            resourcesUtil.openRawResourceFd(id)

    @Throws(NotFoundException::class)
    override fun getDimension(@DimenRes id: Int): Float = resourcesUtil.getDimension(id)

    @Throws(NotFoundException::class)
    override fun getColorStateList(@ColorRes id: Int): ColorStateList = resourcesUtil.getColorStateList(id)

    @TargetApi(Build.VERSION_CODES.M)
    override fun getColorStateList(@ColorRes id: Int, theme: Theme?): ColorStateList =
            resourcesUtil.getColorStateList(id, theme)

    @Throws(NotFoundException::class)
    override fun getBoolean(@BoolRes id: Int): Boolean = resourcesUtil.getBoolean(id)

    override fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int =
            resourcesUtil.getIdentifier(name, defType, defPackage)

    @ColorInt
    @Throws(NotFoundException::class)
    override fun getColor(@ColorRes id: Int): Int = resourcesUtil.getColor(id)

    @TargetApi(Build.VERSION_CODES.M)
    @ColorInt
    @Throws(NotFoundException::class)
    override fun getColor(@ColorRes id: Int, theme: Theme?): Int = resourcesUtil.getColor(id, theme)

    override fun updateConfiguration(config: Configuration?, metrics: DisplayMetrics?) {
        Handler().post { resourcesUtil.updateConfiguration(config, metrics) }
    }

    @Throws(NotFoundException::class)
    override fun openRawResource(@RawRes id: Int): InputStream = resourcesUtil.openRawResource(id)

    @Throws(NotFoundException::class)
    override fun openRawResource(@RawRes id: Int, value: TypedValue?): InputStream =
            resourcesUtil.openRawResource(id, value)

    @Throws(NotFoundException::class)
    override fun getMovie(@RawRes id: Int): Movie = resourcesUtil.getMovie(id)

    @Throws(NotFoundException::class)
    override fun getInteger(@IntegerRes id: Int): Int = resourcesUtil.getInteger(id)

    @Throws(XmlPullParserException::class, IOException::class)
    override fun parseBundleExtras(parser: XmlResourceParser?, outBundle: Bundle?) {
        this.resourcesUtil.parseBundleExtras(parser, outBundle)
    }

    @Throws(NotFoundException::class)
    override fun getDrawable(@DrawableRes id: Int): Drawable = resourcesUtil.getDrawable(id)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Throws(NotFoundException::class)
    override fun getDrawable(@DrawableRes id: Int, theme: Theme?): Drawable =
            resourcesUtil.getDrawable(id, theme)

    @Throws(NotFoundException::class)
    override fun getResourceTypeName(@AnyRes resid: Int): String = resourcesUtil.getResourceTypeName(resid)

    @Throws(NotFoundException::class)
    override fun getLayout(@LayoutRes id: Int): XmlResourceParser = resourcesUtil.getLayout(id)

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NewApi")
    @Throws(NotFoundException::class)
    override fun getFont(@FontRes id: Int): Typeface = resourcesUtil.getFont(id)

    @Throws(NotFoundException::class)
    override fun getXml(@XmlRes id: Int): XmlResourceParser = resourcesUtil.getXml(id)

    @Throws(NotFoundException::class)
    override fun getResourceName(@AnyRes resid: Int): String = resourcesUtil.getResourceName(resid)

    @Throws(XmlPullParserException::class)
    override fun parseBundleExtra(tagName: String?, attrs: AttributeSet?, outBundle: Bundle?) {
        resourcesUtil.parseBundleExtra(tagName, attrs, outBundle)
    }

    @Throws(NotFoundException::class)
    override fun getDimensionPixelOffset(@DimenRes id: Int): Int = resourcesUtil.getDimensionPixelOffset(id)

    @Throws(NotFoundException::class)
    override fun getValueForDensity(@AnyRes id: Int, density: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        resourcesUtil.getValueForDensity(id, density, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    override fun getResourceEntryName(@AnyRes resid: Int): String = resourcesUtil.getResourceEntryName(resid)

    @Throws(NotFoundException::class)
    override fun getFraction(@FractionRes id: Int, base: Int, pbase: Int): Float =
            resourcesUtil.getFraction(id, base, pbase)
}