package com.alayon.countryquizapp

object Constants{

    fun getQuestions(): ArrayList<Question>{
        val questionList = ArrayList<Question>()
        val gralQuestion:String="What country does this flag belong to?"

        val question1 = Question(1, gralQuestion,
                                R.drawable.ic_flag_of_argentina,
                                "Argentina", "Australia",
                                "Armenia", "Austria",
                                1)

        val question2 = Question(2, gralQuestion,
            R.drawable.ic_flag_of_australia,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question3 = Question(3, gralQuestion,
            R.drawable.ic_flag_of_belgium,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question4 = Question(4, gralQuestion,
            R.drawable.ic_flag_of_brazil,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question5 = Question(5, gralQuestion,
            R.drawable.ic_flag_of_denmark,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question6 = Question(6, gralQuestion,
            R.drawable.ic_flag_of_fiji,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question7 = Question(7, gralQuestion,
            R.drawable.ic_flag_of_germany,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question8 = Question(8, gralQuestion,
            R.drawable.ic_flag_of_india,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question9 = Question(9, gralQuestion,
            R.drawable.ic_flag_of_kuwait,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        val question10 = Question(10, gralQuestion,
            R.drawable.ic_flag_of_new_zealand,
            "Argentina", "Australia",
            "Armenia", "Austria",
            1)

        questionList.add(question1)
        questionList.add(question2)
        questionList.add(question3)
        questionList.add(question4)
        questionList.add(question5)
        questionList.add(question6)
        questionList.add(question7)
        questionList.add(question8)
        questionList.add(question9)
        questionList.add(question10)
        return questionList
    }
}