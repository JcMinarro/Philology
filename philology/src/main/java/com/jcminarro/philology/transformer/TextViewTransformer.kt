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
        attributeSet.forEach { index ->
            when (attributeSet.getAttributeName(index)) {
                TEXT -> setTextIfExists(attributeSet, index, this::setText)
                HINT -> setTextIfExists(attributeSet, index, this::setHint)
                STYLE -> setTextIfExistsInStyle(
                    context = context,
                    attributeSet = attributeSet,
                    styleIndex = index,
                    attributesFromStyle = intArrayOf(android.R.attr.text, android.R.attr.hint),
                    setTextResActions = listOf(this::setText, this::setHint)
                )
            }
        }
    }
}
