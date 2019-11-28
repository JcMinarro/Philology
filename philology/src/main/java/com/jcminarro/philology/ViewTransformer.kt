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
        setTextResAction: (Int) -> Unit
    ) {
        val styleResource = attributeSet.getAttributeResourceValue(index, -1).takeIf { it != -1 }
        styleResource?.let {
            val styleAttr = context.obtainStyledAttributes(
                styleResource,
                intArrayOf(android.R.attr.text)
            )
            val textResource = styleAttr.getResourceId(0, 0)
            styleAttr.recycle()
            setTextResAction(textResource)
        }
    }
}
