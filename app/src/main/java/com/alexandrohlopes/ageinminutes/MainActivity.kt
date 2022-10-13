package com.alexandrohlopes.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            {
                    _, selectedYear, selectedMonth, selectedDayOfTheMonth -> Toast.makeText(this,
                "Selected date is $selectedDayOfTheMonth/${selectedMonth + 1}/$selectedYear", // For some strange reason, the months are accounted starting from zero
                Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfTheMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate // You cannot use a nullable without a question mark. Here we are setting the property directly.

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) // Formats the date
                val theDate = sdf.parse(selectedDate) // This is the date object that will be used to calculate the age in minutes
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000 // Converts the selected date to minutes
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) // Gets the actual date in milliseconds
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000 // Converts the current date to minutes
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString() // Converts the result to string and sets it to the Textview
                    }

                }





            }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // Limits the date to yesterday
        dpd.show()



    }
}