package com.easyprog.android.geomain

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

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

    private var answerIsTrue = false
    private var isCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        isCheater = savedInstanceState?.getBoolean(KEY_IS_CHEATER) ?: false

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            val answerText = when(answerIsTrue) {
                true -> R.string.true_button
                false -> R.string.false_button
            }
            answerTextView.setText(answerText)
            isCheater = true
            setAnswerShownResult()
        }

        if (isCheater) setAnswerShownResult()
    }

    private fun setAnswerShownResult() {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_CHEATER, isCheater)
    }
}