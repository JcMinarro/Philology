package com.jcminarro.philology.view

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.jcminarro.sample.R

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val textView: TextView by lazy {
        findViewById<TextView>(R.id.text)
    }

    init {
        View.inflate(context, R.layout.layout_custom_view, this)
        val width = resources.getDimensionPixelSize(R.dimen.text_width)
        textView.text = "Width = " + width
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        val width = resources.getDimensionPixelSize(R.dimen.text_width)
        textView.text = "Width = " + width
    }
}
