package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.jcminarro.philology.ViewTransformer

internal object SupportToolbarViewTransformer : ViewTransformer {
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is Toolbar -> reword(attributeSet)
        }
    }

    private fun Toolbar.reword(attributeSet: AttributeSet) {
        @StringRes val titleResId =
            context.getStringResourceId(attributeSet, android.R.attr.title)
        @StringRes val titleCompatResId =
            context.getStringResourceId(attributeSet, androidx.appcompat.R.attr.title)
        @StringRes val subTitleResId =
            context.getStringResourceId(attributeSet, android.R.attr.title)
        @StringRes val subTitleCompatResId =
            context.getStringResourceId(attributeSet, androidx.appcompat.R.attr.title)

        if (titleResId > 0) setTitle(titleResId)
        else if (titleCompatResId > 0) setTitle(titleCompatResId)

        if (subTitleResId > 0) setSubtitle(subTitleResId)
        else if (subTitleCompatResId > 0) setSubtitle(subTitleCompatResId)
    }
}
