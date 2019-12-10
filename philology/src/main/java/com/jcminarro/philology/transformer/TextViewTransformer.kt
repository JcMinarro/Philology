package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.jcminarro.philology.ViewTransformer

internal object TextViewTransformer : ViewTransformer {
    private const val TEXT = "text"
    private const val HINT = "hint"
    private const val STYLE = "style"
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is TextView -> reword(attributeSet)
        }
    }

    private fun TextView.reword(attributeSet: AttributeSet) {
        var titleIndex: Int? = null
        var hintIndex: Int? = null
        var styleIndex: Int? = null
        attributeSet.forEach { index ->
            when (attributeSet.getAttributeName(index)) {
                TEXT -> titleIndex = index
                HINT -> hintIndex = index
                STYLE -> styleIndex = index
            }
        }
        setTextIfExists(
            context,
            titleIndex,
            styleIndex,
            attributeSet,
            android.R.attr.text,
            this::setText
        )
        setTextIfExists(
            context,
            hintIndex,
            styleIndex,
            attributeSet,
            android.R.attr.hint,
            this::setHint
        )
    }
}
