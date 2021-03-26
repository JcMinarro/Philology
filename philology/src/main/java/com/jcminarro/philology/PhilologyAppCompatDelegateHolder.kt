package com.jcminarro.philology

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.BaseContextWrappingDelegate

class PhilologyAppCompatDelegateHolder {

    private var baseContextWrappingDelegate: AppCompatDelegate? = null

    fun getDelegate(
        superDelegate: AppCompatDelegate,
        onAttachBaseContext: (Context) -> Context
    ) = baseContextWrappingDelegate ?: BaseContextWrappingDelegate(
        superDelegate, onAttachBaseContext
    ).apply { baseContextWrappingDelegate = this }
}
