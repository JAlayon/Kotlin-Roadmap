package com.alayon.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker.setOnClickListener {view->
            clickDatePicker(view)
        }
    }

    fun clickDatePicker(view:View){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear"
                txtViewSelectedDate.setText(selectedDate)
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val date = simpleDateFormat.parse(selectedDate)
                val selectedDateInMins = date!!.time / 60000
                val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                val currentDateInMins = currentDate!!.time / 60000
                val differenceInMins = currentDateInMins - selectedDateInMins
                txtAgeInMinutes.setText("$differenceInMins")
            },
            year, month, dayOfMonth)

        datePickerDialog.datePicker.setMaxDate(Date().time - 86400000)
        datePickerDialog.show()
    }
}