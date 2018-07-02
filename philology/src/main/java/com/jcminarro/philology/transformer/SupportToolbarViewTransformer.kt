package com.jcminarro.philology.transformer

import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import com.jcminarro.philology.ViewTransformer

internal object SupportToolbarViewTransformer : ViewTransformer {
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