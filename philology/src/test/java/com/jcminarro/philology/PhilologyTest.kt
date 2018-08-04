package com.jcminarro.philology

import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.jcminarro.philology.transformer.NoneViewTransformer
import com.jcminarro.philology.transformer.SupportToolbarViewTransformer
import com.jcminarro.philology.transformer.TextViewTransformer
import com.jcminarro.philology.transformer.ToolbarViewTransformer
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.amshove.kluent.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.application

@RunWith(RobolectricTestRunner::class)
class PhilologyTest {

    @Test
    fun `Should return a PhilologyContextWrapper`() {
        Philology.wrap(application) `should be instance of` PhilologyContextWrapper::class
    }

    @Test
    fun `Should provide a NonViewTransformer`() {
        Philology.getViewTransformer(mock()) `should be` NoneViewTransformer
    }

    @Test
    fun `Should provide a SupportToolbarViewTransformer`() {
        Philology.getViewTransformer(mock<Toolbar>()) `should be` SupportToolbarViewTransformer
    }

    @Test
    fun `Should provide a ToolbarViewTransformer`() {
        Philology.getViewTransformer(mock<android.widget.Toolbar>()) `should be` ToolbarViewTransformer
    }

    @Test
    fun `Should provide a TextViewTransformer`() {
        Philology.getViewTransformer(mock<TextView>()) `should be` TextViewTransformer
    }
}