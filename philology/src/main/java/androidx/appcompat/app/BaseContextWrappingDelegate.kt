package androidx.appcompat.app

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.Toolbar

/**
 * Solution for supporting AppCompat 1.2.0.
 * Note: class must be inside the androidx.appcompat.app package because the only existing AppCompatDelegate constructor is package private
 *
 * @param superDelegate original AppCompatDelegate obtained by calling `super.getDelegate()`
 * from `AppCompatActivity`
 * @param onAttachBaseContext called by the AppCompat library. Make sure the context is wrapped in
 * this lambda.
 *
 * @see <a href="https://stackoverflow.com/questions/55265834/change-locale-not-work-after-migrate-to-androidx">stackoverflow</a>
 * @see androidx.appcompat.app.AppCompatActivity.getDelegate
 */
internal class BaseContextWrappingDelegate(
    private val superDelegate: AppCompatDelegate,
    private val onAttachBaseContext: (Context) -> Context
) : AppCompatDelegate() {

    override fun getSupportActionBar() = superDelegate.supportActionBar

    override fun setSupportActionBar(toolbar: Toolbar?) = superDelegate.setSupportActionBar(toolbar)

    override fun getMenuInflater(): MenuInflater? = superDelegate.menuInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        superDelegate.onCreate(savedInstanceState)
        removeActivityDelegate(superDelegate)
        addActiveDelegate(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) = superDelegate.onPostCreate(
        savedInstanceState
    )

    override fun onConfigurationChanged(
        newConfig: Configuration?
    ) = superDelegate.onConfigurationChanged(newConfig)

    override fun onStart() = superDelegate.onStart()

    override fun onStop() = superDelegate.onStop()

    override fun onPostResume() = superDelegate.onPostResume()

    override fun setTheme(themeResId: Int) = superDelegate.setTheme(themeResId)

    override fun <T : View?> findViewById(id: Int) = superDelegate.findViewById<T>(id)

    override fun setContentView(v: View?) = superDelegate.setContentView(v)

    override fun setContentView(resId: Int) = superDelegate.setContentView(resId)

    override fun setContentView(
        v: View?, lp: ViewGroup.LayoutParams?
    ) = superDelegate.setContentView(v, lp)

    override fun addContentView(
        v: View?, lp: ViewGroup.LayoutParams?
    ) = superDelegate.addContentView(v, lp)

    override fun attachBaseContext2(context: Context) = onAttachBaseContext(
        superDelegate.attachBaseContext2(super.attachBaseContext2(context))
    )

    override fun setTitle(title: CharSequence?) = superDelegate.setTitle(title)

    override fun invalidateOptionsMenu() = superDelegate.invalidateOptionsMenu()

    override fun onDestroy() {
        superDelegate.onDestroy()
        removeActivityDelegate(this)
    }

    override fun getDrawerToggleDelegate() = superDelegate.drawerToggleDelegate

    override fun requestWindowFeature(featureId: Int) = superDelegate.requestWindowFeature(
        featureId
    )

    override fun hasWindowFeature(featureId: Int) = superDelegate.hasWindowFeature(featureId)

    override fun startSupportActionMode(
        callback: ActionMode.Callback
    ) = superDelegate.startSupportActionMode(callback)

    override fun installViewFactory() = superDelegate.installViewFactory()

    override fun createView(
        parent: View?, name: String?, context: Context, attrs: AttributeSet
    ): View? = superDelegate.createView(parent, name, context, attrs)

    override fun setHandleNativeActionModesEnabled(enabled: Boolean) {
        superDelegate.isHandleNativeActionModesEnabled = enabled
    }

    override fun isHandleNativeActionModesEnabled() = superDelegate.isHandleNativeActionModesEnabled

    override fun onSaveInstanceState(outState: Bundle?) = superDelegate.onSaveInstanceState(
        outState
    )

    override fun applyDayNight() = superDelegate.applyDayNight()

    @RequiresApi(17)
    override fun setLocalNightMode(mode: Int) {
        superDelegate.localNightMode = mode
    }

    override fun getLocalNightMode() = superDelegate.localNightMode
}
