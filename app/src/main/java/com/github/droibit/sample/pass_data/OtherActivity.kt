package com.github.droibit.sample.pass_data

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_other.*

class OtherActivity : AppCompatActivity(R.layout.activity_other) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backButton.setOnClickListener {
            val result = DetailResult(message = "Pass data from Other")
            val data = Intent().putExtras(result.toBundle())
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    class StartOtherForResult : ActivityResultContract<Void, OtherResult?>() {

        override fun createIntent(context: Context, input: Void?): Intent {
            return Intent(context, OtherActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): OtherResult? {
            if (resultCode == Activity.RESULT_OK && intent != null) {
                return OtherResult(data = requireNotNull(intent.extras))
            }
            return null
        }
    }

    companion object {

        fun createIntent(context: Context) = Intent(context, OtherActivity::class.java)
    }
}

data class OtherResult(val message: String) {
    constructor(data: Bundle) : this(data.getString(KEY, ""))

    fun toBundle(): Bundle = bundleOf(KEY to message)

    companion object {
        const val KEY = "message"
    }
}