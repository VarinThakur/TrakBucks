package com.example.trakbucks

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.databinding.FragmentAddTransactionBinding
import java.util.*
import com.example.trakbucks.TimePickerFragment
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.data.TransactionViewModel
import com.mikhaellopez.circularimageview.CircularImageView

class AddTransactionScreen : Fragment() {
    private var _binding : FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var myTransactionViewModel: TransactionViewModel

    override fun onResume() {
        super.onResume()
        val items = listOf(1,5,10,20,50,100,500,1000,2000,10000)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_dropdown, items)
        (binding.addTransactionAmount.editText as? AutoCompleteTextView)?.setAdapter(adapter)

//        binding.addTransactionAmount.error = "Amount can't be 0."
//        binding.addTransactionName.error = "Name can't be empty."

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        myTransactionViewModel= ViewModelProvider(this).get(TransactionViewModel::class.java)

        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.addTransactionfragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addTransaction(){

        val image: Int = binding.addTransactionImage.id
        val name: String = binding.addTransactionName.editText?.text.toString()
        val type: Int

        if(binding.creditButton.isChecked)
            type=1
        else if(binding.debitButton.isChecked)
            type=2
        else
            type=0

        val amount: String = binding.addTransactionAmount.editText?.text.toString()

        val date: String= binding.addDate.editText?.text.toString()
        val time: String= binding.addTime.editText?.text.toString()

        if(input_check(name, type, amount, date, time))
        {
            //Create Transaction Object
            val transaction= Transaction(0,image,name,amount, date, time, type)
            myTransactionViewModel.addTransaction(transaction)

            Toast.makeText(activity, "Added Transaction successfully.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addTransactionScreen_to_transactionListFragment)
        }
        else
        {
            Toast.makeText(activity, "Please fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun input_check(name : String, type:Int, amount:String, date: String, time: String): Boolean{
        if(type==0)
            return false

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(amount) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time))
            return true

        return false
    }

    fun addImage(){
        Toast.makeText(activity, "Added image successfully", Toast.LENGTH_SHORT).show()
    }

    fun setDate(){
        // create new instance of DatePickerFragment
        val datePickerFragment = DatePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager

        // we have to implement setFragmentResultListener
        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                val date = bundle.getString("SELECTED_DATE")
                binding.addDate.editText!!.setText(date)
            }
        }

        // show
        datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
    }

    fun setTime(){
        // create new instance of TimePickerFragment
        val timePickerFragment = TimePickerFragment()
        val supportFragmentManager = requireActivity().supportFragmentManager

        // we have to implement setFragmentResultListener
        supportFragmentManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                val time = bundle.getString("SELECTED_TIME")
                binding.addTime.editText!!.setText(time)
            }
        }

        //show
        timePickerFragment.show(supportFragmentManager, "TimePickerFragment")
    }

    fun cancelTransaction(){
        findNavController().navigate(R.id.action_addTransactionScreen_to_transactionListFragment)
    }
}