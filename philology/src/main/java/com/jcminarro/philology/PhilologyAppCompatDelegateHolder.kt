package com.jcminarro.philology

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.BaseContextWrappingDelegate
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class PhilologyAppCompatDelegateHolder {

    private var baseContextWrappingDelegate: AppCompatDelegate? = null

    fun getDelegate(superDelegate: AppCompatDelegate) = baseContextWrappingDelegate
        ?: BaseContextWrappingDelegate(superDelegate) { context ->
            ViewPumpContextWrapper.wrap(Philology.wrap(context))
        }.apply { baseContextWrappingDelegate = this }
}
