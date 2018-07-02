package com.jcminarro.philology.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jcminarro.philology.Philology
import com.jcminarro.sample.R
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(Philology.wrap(newBase)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
