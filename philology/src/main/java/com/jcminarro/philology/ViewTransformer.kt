package com.jcminarro.philology

import android.util.AttributeSet
import android.view.View

interface ViewTransformer{
    fun reword(view: View, attributeSet: AttributeSet): View

    fun setTextIfExists(attributeSet: AttributeSet, index: Int, setTextResAction: (Int) -> Unit){
        attributeSet.getAttributeResourceValue(index, -1).takeIf {it != -1}?.let {setTextResAction(it)}
    }
}