package com.jcminarro.philology.transformer

import android.util.AttributeSet
import android.view.View
import org.amshove.kluent.`Verify no further interactions`
import org.amshove.kluent.`should be`
import org.amshove.kluent.mock
import org.amshove.kluent.on
import org.junit.Test

class NoneViewTransformerTest {
    private val view: View = mock()
    private val attributeSet: AttributeSet = mock()

    @Test
    fun `View should be the same`() {
        NoneViewTransformer.reword(view, attributeSet) `should be` view
    }

    @Test
    fun `View shouldn't be modified`() {
        NoneViewTransformer.reword(view, attributeSet)

        `Verify no further interactions` on view
    }
}