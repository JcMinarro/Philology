package com.jcminarro.philology.transformer

import android.util.AttributeSet

fun AttributeSet.forEach(action: (index: Int) -> Unit) {
    (0 until attributeCount).forEach(action)
}