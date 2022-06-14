package com.example.trakbucks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.data.*
import com.example.trakbucks.databinding.FragmentAddTransactionBinding
import java.util.*
import com.example.trakbucks.TimePickerFragment
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory
import com.mikhaellopez.circularimageview.CircularImageView
import com.github.dhaval2404.imagepicker.ImagePicker

class AddTransactionScreen : Fragment() {
    private var _binding : FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private var uri: Uri = Uri.parse("android.resource://com.example.trakbucks/" + R.drawable.ic_baseline_person_24)

    private val myTransactionViewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            (activity?.application as TransactionApplication).database
                .transactionDao()
        )
    }

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
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTransactionfragment = this

        binding.addDate.editText?.setOnFocusChangeListener{View, hasFocus -> setDate(hasFocus) }
        binding.addTime.editText?.setOnFocusChangeListener{View, hasFocus -> setTime(hasFocus) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.addTransactionImage.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    fun addTransaction(){

        val image: String = uri.toString()
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

        if(TextUtils.isEmpty(name))
        {
            binding.addTransactionName.error = "Name can't be empty."
        }
        else
        {
            binding.addTransactionName.error = null
        }
        if(TextUtils.isEmpty(amount))
        {
            binding.addTransactionAmount.error = "Amount can't be empty."
        }
        else
        {
            binding.addTransactionAmount.error = null
        }
        if(TextUtils.isEmpty(date))
        {
            binding.addDate.error = "Date can't be empty."
        }
        else
        {
            binding.addDate.error = null
        }
        if(TextUtils.isEmpty(time))
        {
            binding.addTime.error = "Time can't be empty."
        }
        else
        {
            binding.addTime.error = null
        }

        if(type != 0 &&
            !TextUtils.isEmpty(name) &&
            !TextUtils.isEmpty(amount) &&
            !TextUtils.isEmpty(date) &&
            !TextUtils.isEmpty(time))
            return true

        return false
    }

    fun addImage(){
//        Toast.makeText(activity, "Added image successfully", Toast.LENGTH_SHORT).show()
        ImagePicker.with(this)
            .galleryOnly()	//User can only select image from Gallery
            .cropSquare()	//Crop square image, its same as crop(1f, 1f)
            .start()
    }

    fun setDate(hasFocus : Boolean){
        if(hasFocus)
        {
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
    }

    fun setTime(hasFocus: Boolean){

        if(hasFocus){
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
    }

    fun cancelTransaction(){
        findNavController().navigate(R.id.action_addTransactionScreen_to_transactionListFragment)
    }
}