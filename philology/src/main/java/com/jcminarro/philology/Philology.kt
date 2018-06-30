package com.jcminarro.philology

import android.content.Context
import android.content.ContextWrapper

object Philology {
    fun wrap(baseContext: Context): ContextWrapper = PhilologyContextWrapper(baseContext)
}