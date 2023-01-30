package com.easyprog.android.geomain.activity.main_activity

import androidx.lifecycle.ViewModel
import com.easyprog.android.geomain.model.Question
import com.easyprog.android.geomain.R

class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var currentIndex = 0
    var score = 0
    var isCheater = false
    var countHints = 3

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    fun moveToNext(next: Boolean) {
        currentIndex = if (next) {
            isCheater = false
            currentIndex + 1
        } else {
            currentIndex - 1
        }
    }

    fun checkAnswer(userAnswer: Boolean): Int {
        return when {
            isCheater -> R.string.judgment_toast
            currentQuestionAnswer == userAnswer -> {
                score++
                R.string.correct_toast
            }
            else -> R.string.incorrect_toast
        }
    }

    fun getScoreUser(): Int {
        val percentageResponses = (score.toDouble()/currentIndex) * 100
        currentIndex = -1
        score = 0
        return percentageResponses.toInt()
    }
}