package com.jcminarro.philology.transformer

import android.content.Context
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.jcminarro.philology.ResourceIdAttribute.CompatSubtitleAttribute
import com.jcminarro.philology.ResourceIdAttribute.CompatTitleAttribute
import com.jcminarro.philology.ResourceIdAttribute.SubtitleAttribute
import com.jcminarro.philology.ResourceIdAttribute.TitleAttribute
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

private const val TITLE_RES_ID = 1039982
private const val SUBTITLE_RES_ID = 3879817
private const val INVALID_RES_ID = -1

class SupportToolbarViewTransformerTest {

    private val view: View = mock()
    private val toolbar: Toolbar = mock()
    private val context: Context = mock()

    @Before
    fun setUp() {
        When calling toolbar.context doReturn context
    }

    @Test
    fun `View should be the same`() {
        SupportToolbarViewTransformer.reword(view, toolbar.createAttributeSet()) `should be` view
    }

    @Test
    fun `Inflated toolbar should be the same`() {
        SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                TitleAttribute(TITLE_RES_ID),
                SubtitleAttribute(INVALID_RES_ID),
                CompatTitleAttribute(INVALID_RES_ID),
                CompatSubtitleAttribute(INVALID_RES_ID)
            )
        ) `should be` toolbar
    }

    @Test
    fun `View shouldn't be modified`() {
        SupportToolbarViewTransformer.reword(view, toolbar.createAttributeSet())

        `Verify no further interactions` on view
    }

    @Test
    fun `Should reword only the title`() {
        val viewResult = SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                TitleAttribute(TITLE_RES_ID),
                SubtitleAttribute(INVALID_RES_ID),
                CompatTitleAttribute(INVALID_RES_ID),
                CompatSubtitleAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(TITLE_RES_ID) was called
        VerifyNotCalled on toolbar that toolbar.setSubtitle(anyInt())
    }

    @Test
    fun `Should reword only the compat title`() {
        val viewResult = SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                CompatTitleAttribute(TITLE_RES_ID),
                CompatSubtitleAttribute(INVALID_RES_ID),
                TitleAttribute(INVALID_RES_ID),
                SubtitleAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(TITLE_RES_ID) was called
        VerifyNotCalled on toolbar that toolbar.setSubtitle(anyInt())
    }

    @Test
    fun `Should reword only the subtitle`() {
        val viewResult = SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                SubtitleAttribute(SUBTITLE_RES_ID),
                TitleAttribute(INVALID_RES_ID),
                CompatTitleAttribute(INVALID_RES_ID),
                CompatSubtitleAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setSubtitle(SUBTITLE_RES_ID) was called
        VerifyNotCalled on toolbar that toolbar.setTitle(anyInt())
    }

    @Test
    fun `Should reword only the compat subtitle`() {
        val viewResult = SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                CompatSubtitleAttribute(SUBTITLE_RES_ID),
                CompatTitleAttribute(INVALID_RES_ID),
                SubtitleAttribute(INVALID_RES_ID),
                TitleAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setSubtitle(SUBTITLE_RES_ID) was called
        VerifyNotCalled on toolbar that toolbar.setTitle(anyInt())
    }

    @Test
    fun `Should reword title and subtitle`() {
        val viewResult = SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                TitleAttribute(TITLE_RES_ID),
                SubtitleAttribute(SUBTITLE_RES_ID),
                CompatTitleAttribute(INVALID_RES_ID),
                CompatSubtitleAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(TITLE_RES_ID) was called
        Verify on toolbar that toolbar.setSubtitle(SUBTITLE_RES_ID) was called
    }

    @Test
    fun `Should reword compat title and subtitle`() {
        val viewResult = SupportToolbarViewTransformer.reword(
            toolbar, toolbar.createAttributeSet(
                CompatTitleAttribute(TITLE_RES_ID),
                CompatSubtitleAttribute(SUBTITLE_RES_ID),
                TitleAttribute(INVALID_RES_ID),
                SubtitleAttribute(INVALID_RES_ID)
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(TITLE_RES_ID) was called
        Verify on toolbar that toolbar.setSubtitle(SUBTITLE_RES_ID) was called
    }
}
