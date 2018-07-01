package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import com.jcminarro.philology.ViewTransformer

object NoneViewTransformer : ViewTransformer {
    override fun reword(view: View, attributeSet: AttributeSet): View = view
}