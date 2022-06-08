package com.example.trakbucks

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.fixedRateTimer

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(requireActivity(), this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)

        var selectedTime = ""
        if(DateFormat.is24HourFormat(activity))
            selectedTime = SimpleDateFormat("HH:mm",).format(c.time)
        else
            selectedTime = SimpleDateFormat("hh:mm aaa").format(c.time)

        val selectedTimeBundle = Bundle()
        selectedTimeBundle.putString("SELECTED_TIME", selectedTime)

        setFragmentResult("REQUEST_KEY", selectedTimeBundle)
    }
}