package com.jcminarro.philology

import android.content.Context
import android.util.AttributeSet
import android.view.View

interface ViewTransformer {
    fun reword(view: View, attributeSet: AttributeSet): View

    fun setTextIfExists(attributeSet: AttributeSet, index: Int, setTextResAction: (Int) -> Unit) {
        attributeSet.getAttributeResourceValue(index, -1).takeIf { it != -1 }
            ?.let { setTextResAction(it) }
    }

    fun setTextIfExistsInStyle(
        context: Context,
        attributeSet: AttributeSet,
        index: Int,
        setTextResAction: (Int) -> Unit,
        setHintResAction: (Int) -> Unit
    ) {
        val attributes = intArrayOf(android.R.attr.text, android.R.attr.hint)
        val styleResource = attributeSet.getAttributeResourceValue(index, -1).takeIf { it != -1 }
        styleResource?.let {
            val styleAttr = context.obtainStyledAttributes(styleResource, attributes)
            val textResource = styleAttr.getResourceId(attributes.indexOf(android.R.attr.text), -1)
                .takeIf { it != -1 }
            textResource?.let(setTextResAction)
            val hintResource = styleAttr.getResourceId(attributes.indexOf(android.R.attr.hint), -1)
                .takeIf { it != -1 }
            hintResource?.let(setHintResAction)
            styleAttr.recycle()
        }
    }
}
