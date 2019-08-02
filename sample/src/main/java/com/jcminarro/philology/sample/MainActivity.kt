package com.jcminarro.philology.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
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
        val pluralLabel = findViewById<TextView>(R.id.plural_label)
        findViewById<EditText>(R.id.plural_quantity_edit).afterTextChanged { text: String ->
            text.toIntOrNull()?.let {
                pluralLabel.text = resources.getQuantityString(R.plurals.plurals_sample, it)
            }
        }

        val pluralFormatLabel = findViewById<TextView>(R.id.plural_format_label)
        findViewById<EditText>(R.id.plural_quantity_format_edit).afterTextChanged { text: String ->
            text.toIntOrNull()?.let {
                pluralFormatLabel.text = resources.getQuantityString(R.plurals.plurals_sample_format, it, it)
            }
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}
