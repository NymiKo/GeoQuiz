package com.easyprog.android.geomain

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_INDEX = "index"
        private const val KEY_SCORE = "score"
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy { ViewModelProvider(this)[QuizViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        quizViewModel.score = savedInstanceState?.getInt(KEY_SCORE) ?: 0

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
            changeViewEnable(false)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            changeViewEnable(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext(true)
            getResult()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToNext(false)
            getResult()
        }

        questionTextView.setOnClickListener {
            getResult()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val messageResId = quizViewModel.checkAnswer(userAnswer)
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun getResult() {
        if (quizViewModel.currentIndex == 6) {
            changeViewEnable(false)
            questionTextView.text = getString(R.string.result, quizViewModel.getScoreUser())
        } else {
            updateQuestion()
            changeViewEnable(true)
        }
    }

    private fun changeViewEnable(enabled: Boolean) {
        trueButton.isEnabled = enabled
        falseButton.isEnabled = enabled
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        outState.putInt(KEY_SCORE, quizViewModel.score)
    }
}