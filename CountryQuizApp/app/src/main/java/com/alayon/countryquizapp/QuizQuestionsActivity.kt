package com.alayon.countryquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        val questionList = Constants.getQuestions()
        Log.i("Questions size", "${questionList.size}")

        val currentPosition = 1
        val question : Question? = questionList[currentPosition-1]
        progressBar.progress = currentPosition

        txtProgress.text = "$currentPosition / {${progressBar.max}}"

        txtQuestion.text = question!!.question
        imgCountry.setImageResource(question.image)
        txtOptionOne.text = question!!.option1
        txtOptionTwo.text = question!!.option2
        txtOptionThree.text = question!!.option3
        txtOptionFour.text = question!!.option4
    }
}