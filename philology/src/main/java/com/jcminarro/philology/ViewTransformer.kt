package com.jcminarro.philology

import android.content.Context
import android.util.AttributeSet
import android.view.View

interface ViewTransformer {
    fun reword(view: View, attributeSet: AttributeSet): View

    fun setTextIfExists(
        context: Context,
        index: Int?,
        styleIndex: Int?,
        attributeSet: AttributeSet,
        attributeFromStyle: Int,
        setTextResAction: (Int) -> Unit
    ) {
        if (index != null) {
            setTextIfExists(attributeSet, index, setTextResAction)
        } else styleIndex?.let {
            setTextIfExistsInStyle(
                context = context,
                attributeSet = attributeSet,
                styleIndex = styleIndex,
                attributeFromStyle = attributeFromStyle,
                setTextResAction = setTextResAction
            )
        }
    }

    private fun setTextIfExists(
        attributeSet: AttributeSet,
        index: Int,
        setTextResAction: (Int) -> Unit
    ) {
        attributeSet.getAttributeResourceValue(index, -1).takeIf { it != -1 }
            ?.let { setTextResAction(it) }
    }

    private fun setTextIfExistsInStyle(
        context: Context,
        attributeSet: AttributeSet,
        styleIndex: Int,
        attributeFromStyle: Int,
        setTextResAction: (Int) -> Unit
    ) {
        val styleResource =
            attributeSet.getAttributeResourceValue(styleIndex, -1).takeIf { it != -1 }
        styleResource?.let {
            val styleAttr =
                context.obtainStyledAttributes(styleResource, intArrayOf(attributeFromStyle))
            val textResource = styleAttr.getResourceId(0, -1).takeIf { it != -1 }
            textResource?.let(setTextResAction)
            styleAttr.recycle()
        }
    }
}
