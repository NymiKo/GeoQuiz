package com.easyprog.android.geomain.cheat_activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.easyprog.android.geomain.R

class CheatActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "com.easyprog.android.geomain.answer_is_true"
        private const val EXTRA_ANSWER_SHOWN = "com.easyprog.android.geomain.answer_shown"
        private const val KEY_IS_CHEATER = "IS_CHEATER"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var androidVersionTextView: TextView

    private val viewModel: CheatViewModel by lazy { ViewModelProvider(this)[CheatViewModel::class.java] }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        viewModel.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        androidVersionTextView = findViewById(R.id.android_version_text_view)


        showAnswerButton.setOnClickListener {
            val answerText = when(viewModel.answerIsTrue) {
                true -> R.string.true_button
                false -> R.string.false_button
            }
            viewModel.isCheater = true
            answerTextView.setText(answerText)
            setAnswerShownResult()
        }

        if (viewModel.isCheater) setAnswerShownResult()

        androidVersionTextView.text = "API level ${Build.VERSION.SDK_INT}"
    }

    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_OK, data)
    }
}