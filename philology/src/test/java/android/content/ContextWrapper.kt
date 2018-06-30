package android.content

import android.content.res.Resources

abstract class ContextWrapper(private val base: Context) : Context() {
    override fun getResources(): Resources = base.resources
}