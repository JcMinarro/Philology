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
        styleIndex: Int,
        attributesFromStyle: IntArray,
        setTextResActions: List<(Int) -> Unit>
    ) {
        val styleResource =
            attributeSet.getAttributeResourceValue(styleIndex, -1).takeIf { it != -1 }
        styleResource?.let {
            val styleAttr = context.obtainStyledAttributes(styleResource, attributesFromStyle)
            attributesFromStyle.forEachIndexed { index, _ ->
                val textResource = styleAttr.getResourceId(index, -1).takeIf { it != -1 }
                textResource?.let(setTextResActions[index])
            }
            styleAttr.recycle()
        }
    }
}
