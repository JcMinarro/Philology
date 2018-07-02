package com.jcminarro.philology

import android.content.Context
import android.content.ContextWrapper
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.jcminarro.philology.transformer.NoneViewTransformer
import com.jcminarro.philology.transformer.SupportToolbarViewTransformer
import com.jcminarro.philology.transformer.TextViewTransformer
import com.jcminarro.philology.transformer.ToolbarViewTransformer
import java.util.Locale

object Philology {
    private val repositoryMap = mutableMapOf<Locale, PhilologyRepository>()
    private var factory: PhilologyRepositoryFactory = object : PhilologyRepositoryFactory{
        override fun getPhilologyRepository(locale: Locale): PhilologyRepository? = null
    }

    fun init(factory: PhilologyRepositoryFactory) {
        this.factory = factory
    }

    fun wrap(baseContext: Context): ContextWrapper = PhilologyContextWrapper(baseContext)

    internal fun getPhilologyRepository(locale: Locale): PhilologyRepository =
            repositoryMap[locale] ?:
            factory.getPhilologyRepository(locale) ?:
                    emptyPhilologyRepository

    internal fun getViewTransformer(view: View): ViewTransformer = when (view) {
        is Toolbar -> SupportToolbarViewTransformer
        is android.widget.Toolbar -> ToolbarViewTransformer
        is TextView -> TextViewTransformer
        else -> NoneViewTransformer

    }
}

interface PhilologyRepositoryFactory {
    fun getPhilologyRepository(locale: Locale): PhilologyRepository?
}

private val emptyPhilologyRepository = object : PhilologyRepository{
    override fun getText(key: String): CharSequence? = null
}
