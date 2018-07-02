package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import android.widget.Toolbar
import com.jcminarro.philology.ViewTransformer

internal object ToolbarViewTransformer : ViewTransformer {
    private const val TITLE = "title"
    private const val SUBTITLE = "subtitle"
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is Toolbar -> reword(attributeSet)
        }
    }

    private fun Toolbar.reword(attributeSet: AttributeSet) {
        attributeSet.forEach {
            when (attributeSet.getAttributeName(it)) {
                TITLE -> setTextIfExists(attributeSet, it, this::setTitle)
                SUBTITLE -> setTextIfExists(attributeSet, it, this::setSubtitle)
            }
        }
    }
}
