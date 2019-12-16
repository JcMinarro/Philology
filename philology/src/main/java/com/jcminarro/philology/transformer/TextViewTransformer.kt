package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.jcminarro.philology.ViewTransformer

internal object TextViewTransformer : ViewTransformer {
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is TextView -> reword(attributeSet)
        }
    }

    private fun TextView.reword(attributeSet: AttributeSet) {
        @StringRes val textResId =
            context.getStringResourceId(attributeSet, android.R.attr.text)
        @StringRes val hintResId =
            context.getStringResourceId(attributeSet, android.R.attr.hint)

        if (textResId > 0) setText(textResId)
        if (hintResId > 0) setHint(hintResId)
    }
}
