package com.alayon.countryquizapp

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import kotlin.properties.Delegates

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mQuestionList:ArrayList<Question>?=null
    private val options:ArrayList<TextView> = ArrayList()
    private var mCurrentPosition:Int = 1

    private var mSelectedOptionPosition:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mQuestionList = Constants.getQuestions()
        setQuestion()

        txtOptionOne.setOnClickListener(this)
        txtOptionTwo.setOnClickListener(this)
        txtOptionThree.setOnClickListener(this)
        txtOptionFour.setOnClickListener(this)

        btnSubmit.setOnClickListener{
            onSubmit()
        }

    }

    private fun onSubmit(){
        if (mSelectedOptionPosition == 0){
            mCurrentPosition++

            when{
                mCurrentPosition <= mQuestionList!!.size -> {
                    setQuestion()
                }else ->{
                    Toast.makeText(this, "You have successfully completed the Quiz!", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            val question = mQuestionList?.get(mCurrentPosition-1)
            if (question!!.correctAnswer !=  mSelectedOptionPosition){
                answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
            }
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            if(mCurrentPosition == mQuestionList!!.size){
                btnSubmit.text = "FINISH!"
            }else{
                btnSubmit.text = "GO TO NEXT QUESTION"
            }
            mSelectedOptionPosition = 0
        }
    }

    private fun answerView(answer:Int, drawableView:Int){
        val background: Drawable? = ContextCompat.getDrawable(this, drawableView)
        when(answer){
            1 -> txtOptionOne.background = background
            2 -> txtOptionTwo.background = background
            3 -> txtOptionThree.background = background
            4 -> txtOptionFour.background = background
        }
    }

    private fun setQuestion(){
        val question = mQuestionList!![mCurrentPosition-1]

        defaultOptionsView()

        if (mCurrentPosition == mQuestionList!!.size){
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition

        txtProgress.text = "$mCurrentPosition / {${progressBar.max}}"

        txtQuestion.text = question!!.question
        imgCountry.setImageResource(question.image)
        txtOptionOne.text = question!!.option1
        txtOptionTwo.text = question!!.option2
        txtOptionThree.text = question!!.option3
        txtOptionFour.text = question!!.option4

        options.add(0, txtOptionOne)
        options.add(1, txtOptionTwo)
        options.add(2, txtOptionThree)
        options.add(3, txtOptionFour)
    }

    private fun defaultOptionsView(){
        for(option in options){
            option.setTextColor(Color.parseColor("#798089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,
                        R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(view: View?) {
        var selectedOption = 0
        when(view?.id){
            R.id.txtOptionOne -> selectedOption = 1
            R.id.txtOptionTwo -> selectedOption = 2
            R.id.txtOptionThree -> selectedOption = 3
            R.id.txtOptionFour -> selectedOption = 4
        }
        selectedOptionView(view as TextView, selectedOption)
    }

    private fun selectedOptionView(txtView:TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        txtView.setTextColor(Color.parseColor("#363A43"))
        txtView.setTypeface(txtView.typeface, Typeface.BOLD)
        txtView.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_border_bg)
    }


}