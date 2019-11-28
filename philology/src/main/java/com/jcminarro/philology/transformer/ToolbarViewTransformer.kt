package com.jcminarro.philology.transformer

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.view.View
import android.widget.Toolbar
import com.jcminarro.philology.ViewTransformer

@SuppressLint("NewApi")
internal object ToolbarViewTransformer : ViewTransformer {
    private const val TITLE = "title"
    private const val SUBTITLE = "subtitle"
    private const val STYLE = "style"
    override fun reword(view: View, attributeSet: AttributeSet): View = view.apply {
        when (this) {
            is Toolbar -> reword(attributeSet)
        }
    }

    private fun Toolbar.reword(attributeSet: AttributeSet) {
        attributeSet.forEach { index ->
            when (attributeSet.getAttributeName(index)) {
                TITLE -> setTextIfExists(attributeSet, index, this::setTitle)
                SUBTITLE -> setTextIfExists(attributeSet, index, this::setSubtitle)
                STYLE -> setTextIfExistsInStyle(
                    context = context,
                    attributeSet = attributeSet,
                    styleIndex = index,
                    attributesFromStyle = intArrayOf(android.R.attr.title, android.R.attr.subtitle),
                    setTextResActions = listOf(this::setTitle, this::setSubtitle)
                )
            }
        }
    }
}
