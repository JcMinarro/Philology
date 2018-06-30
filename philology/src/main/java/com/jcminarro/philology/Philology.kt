package com.jcminarro.philology

import android.content.Context
import android.content.ContextWrapper
import java.util.Locale

object Philology {
    val repositoryMap = mutableMapOf<Locale, PhilologyRepository>()
    var factory: PhilologyRepositoryFactory = object : PhilologyRepositoryFactory{
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
}

interface PhilologyRepositoryFactory {
    fun getPhilologyRepository(locale: Locale): PhilologyRepository?
}

private val emptyPhilologyRepository = object : PhilologyRepository{
    override fun getText(key: String): CharSequence? = null
}
