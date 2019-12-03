package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.jcminarro.philology.ViewTransformer

internal object SupportToolbarViewTransformer : ViewTransformer {
    private const val TITLE = "title"
    private const val SUBTITLE = "subtitle"
    private const val STYLE = "style"
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is Toolbar -> reword(attributeSet)
        }
    }

    private fun Toolbar.reword(attributeSet: AttributeSet) {
        var titleIndex: Int? = null
        var subtitleIndex: Int? = null
        var styleIndex: Int? = null
        attributeSet.forEach { index ->
            when (attributeSet.getAttributeName(index)) {
                TITLE -> titleIndex = index
                SUBTITLE -> subtitleIndex = index
                STYLE -> styleIndex = index
            }
        }
        setTextIfExists(
            context,
            titleIndex,
            styleIndex,
            attributeSet,
            android.R.attr.title,
            this::setTitle
        )
        setTextIfExists(
            context,
            subtitleIndex,
            styleIndex,
            attributeSet,
            android.R.attr.subtitle,
            this::setSubtitle
        )
    }
}
