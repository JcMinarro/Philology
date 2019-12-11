package com.jcminarro.philology.transformer

import android.content.Context
import android.view.View
import android.widget.TextView
import com.jcminarro.philology.ResourceIdAttribute.HintAttribute
import com.jcminarro.philology.ResourceIdAttribute.TextAttribute
import com.jcminarro.philology.createAttributeSet
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.Verify
import org.amshove.kluent.VerifyNotCalled
import org.amshove.kluent.When
import org.amshove.kluent.`Verify no further interactions`
import org.amshove.kluent.`should be`
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.mock
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

private const val TEXT_RES_ID = 1039982
private const val HINT_RES_ID = 3879817
private const val INVALID_RES_ID = -1

class TextViewTransformerTest {

    private val view: View = mock()
    private val textView: TextView = mock()
    private val context: Context = mock()

    @Before
    fun setUp() {
        When calling textView.context doReturn context
    }

    @Test
    fun `View should be the same`() {
        TextViewTransformer.reword(view, textView.createAttributeSet()) `should be` view
    }

    @Test
    fun `Inflated textView should be the same`() {
        TextViewTransformer.reword(
            textView, textView.createAttributeSet(
                TextAttribute(INVALID_RES_ID),
                HintAttribute(INVALID_RES_ID)
            )
        ) `should be` textView
    }

    @Test
    fun `View shouldn't be modified`() {
        TextViewTransformer.reword(view, textView.createAttributeSet())

        `Verify no further interactions` on view
    }

    @Test
    fun `Should reword only the text`() {
        val viewResult = TextViewTransformer.reword(
            textView, textView.createAttributeSet(
                TextAttribute(TEXT_RES_ID),
                HintAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` textView
        Verify on textView that textView.setText(TEXT_RES_ID) was called
        VerifyNotCalled on textView that textView.setHint(anyInt())
    }

    @Test
    fun `Should reword only the hint`() {
        val viewResult = TextViewTransformer.reword(
            textView, textView.createAttributeSet(
                TextAttribute(INVALID_RES_ID),
                HintAttribute(HINT_RES_ID)
            )
        )

        viewResult `should be` textView
        Verify on textView that textView.setHint(HINT_RES_ID) was called
        VerifyNotCalled on textView that textView.setText(anyInt())
    }

    @Test
    fun `Should reword text and hint`() {
        val viewResult = TextViewTransformer.reword(
            textView, textView.createAttributeSet(
                TextAttribute(TEXT_RES_ID),
                HintAttribute(HINT_RES_ID)
            )
        )

        viewResult `should be` textView
        Verify on textView that textView.setText(TEXT_RES_ID) was called
        Verify on textView that textView.setHint(HINT_RES_ID) was called
    }
}
