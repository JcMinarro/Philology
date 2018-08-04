package com.jcminarro.philology

import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR2
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.LOLLIPOP_MR1
import android.os.Build.VERSION_CODES.M
import android.os.Build.VERSION_CODES.N
import android.os.Build.VERSION_CODES.N_MR1
import android.os.Build.VERSION_CODES.O
import android.os.Build.VERSION_CODES.O_MR1
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
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [JELLY_BEAN, JELLY_BEAN_MR1, JELLY_BEAN_MR2, KITKAT, LOLLIPOP, LOLLIPOP_MR1, M, N, N_MR1, O, O_MR1])
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
    @Config(sdk = [LOLLIPOP, LOLLIPOP_MR1, M, N, N_MR1, O, O_MR1])
    fun `Should provide a ToolbarViewTransformer`() {
        Philology.getViewTransformer(mock<android.widget.Toolbar>()) `should be` ToolbarViewTransformer
    }

    @Test
    @Config(sdk = [JELLY_BEAN, JELLY_BEAN_MR1, JELLY_BEAN_MR2, KITKAT])
    fun `Shouldn't provide a ToolbarViewTransformer because Toolbar doesn't exist on those APIs`() {
        Philology.getViewTransformer(mock<android.widget.Toolbar>()) `should be` NoneViewTransformer
    }

    @Test
    fun `Should provide a TextViewTransformer`() {
        Philology.getViewTransformer(mock<TextView>()) `should be` TextViewTransformer
    }
}