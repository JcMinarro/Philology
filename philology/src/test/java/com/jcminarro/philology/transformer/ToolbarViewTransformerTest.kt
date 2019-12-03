package com.jcminarro.philology.transformer

import android.content.Context
import android.view.View
import android.widget.Toolbar
import com.jcminarro.philology.HardcodedAttribute
import com.jcminarro.philology.ResourceIdAttribute
import com.jcminarro.philology.StyleAttribute
import com.jcminarro.philology.createAttributeSet
import com.nhaarman.mockito_kotlin.doReturn
import org.amshove.kluent.Verify
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

class ToolbarViewTransformerTest {

    private val view: View = mock()
    private val toolbar: Toolbar = mock()
    private val context: Context = mock()

    @Before
    fun setUp() {
        When calling toolbar.context doReturn context
    }

    @Test
    fun `View should be the same`() {
        ToolbarViewTransformer.reword(view, createAttributeSet()) `should be` view
    }

    @Test
    fun `Inflated toolbar should be the same`() {
        ToolbarViewTransformer.reword(toolbar, createAttributeSet()) `should be` toolbar
    }

    @Test
    fun `View shouldn't be modified`() {
        ToolbarViewTransformer.reword(view, createAttributeSet())

        `Verify no further interactions` on view
    }

    @Test
    fun `Should reword only the title`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                HardcodedAttribute("subtitle"),
                ResourceIdAttribute("title")
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(1) was called
    }

    @Test
    fun `Should reword only the subtitle`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                HardcodedAttribute("title"),
                ResourceIdAttribute("subtitle")
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setSubtitle(1) was called
    }

    @Test
    fun `Should reword title and subtitle`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                ResourceIdAttribute("title"),
                ResourceIdAttribute("subtitle")
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(0) was called
        Verify on toolbar that toolbar.setSubtitle(1) was called
    }

    @Test
    fun `Should reword only the title from style`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                StyleAttribute(intArrayOf(android.R.attr.title)),
                context = context
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(0) was called
    }

    @Test
    fun `Should reword only the subtitle from style`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                StyleAttribute(intArrayOf(android.R.attr.subtitle)),
                context = context
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setSubtitle(0) was called
    }

    @Test
    fun `Should reword title and subtitle from style`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                StyleAttribute(intArrayOf(android.R.attr.title, android.R.attr.subtitle)),
                context = context
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(0) was called
        Verify on toolbar that toolbar.setSubtitle(0) was called
    }

    @Test
    fun `Should reword title and subtitle from attributes ignoring style`() {
        val viewResult = ToolbarViewTransformer.reword(
            toolbar, createAttributeSet(
                ResourceIdAttribute("title"),
                ResourceIdAttribute("subtitle"),
                StyleAttribute(intArrayOf(android.R.attr.title, android.R.attr.subtitle)),
                context = context
            )
        )

        viewResult `should be` toolbar
        Verify on toolbar that toolbar.setTitle(0) was called
        Verify on toolbar that toolbar.setSubtitle(1) was called
    }
}
