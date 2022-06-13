package com.example.trakbucks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trakbucks.R
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory
import com.example.trakbucks.databinding.FragmentAddTransactionBinding
import com.example.trakbucks.databinding.FragmentUpdateTransactionScreenBinding
import com.github.dhaval2404.imagepicker.ImagePicker

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateTransactionScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateTransactionScreen : Fragment() {

    private val args by navArgs<UpdateTransactionScreenArgs>()

    private var _binding : FragmentUpdateTransactionScreenBinding? = null
    private val binding get() = _binding!!

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
        (binding.updateTransactionAmount.editText as? AutoCompleteTextView)?.setAdapter(adapter)

//        binding.addTransactionAmount.error = "Amount can't be 0."
//        binding.addTransactionName.error = "Name can't be empty."

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentUpdateTransactionScreenBinding.inflate(inflater, container, false)
        _binding = fragmentBinding

        binding.updateTransactionName.editText?.setText(args.currentTransaction.name)
        binding.updateTransactionAmount.editText?.setText(args.currentTransaction.amount)
        binding.updateDate.editText?.setText(args.currentTransaction.date)
        binding.updateTime.editText?.setText(args.currentTransaction.time)
        if(args.currentTransaction.type==1)
            binding.creditButton.isChecked = true
        else if(args.currentTransaction.type==2)
            binding.debitButton.isChecked = true



        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.updateTransactionScreenfragment = this

        binding.updateDate.editText?.setOnFocusChangeListener{View, hasFocus -> setDate(hasFocus) }
        binding.updateTime.editText?.setOnFocusChangeListener{View, hasFocus -> setTime(hasFocus) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            binding.updateTransactionImage.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateTransaction(){

        val image: Int = binding.updateTransactionImage.id
        val name = binding.updateTransactionName.editText?.text.toString()
        val amount = binding.updateTransactionAmount.editText?.text.toString()
        val date = binding.updateDate.editText?.text.toString()
        val time = binding.updateTime.editText?.text.toString()
        val type: Int

        if(binding.creditButton.isChecked)
            type=1
        else if(binding.debitButton.isChecked)
            type=2
        else
            type=0

        if(input_check(name, type, amount, date, time)){
            val updatedTransaction = Transaction(args.currentTransaction.id,image,name, amount, date, time, type)
            myTransactionViewModel.updateTransaction(updatedTransaction)
            Toast.makeText(activity, "Updated Transaction successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateTransactionScreen_to_transactionListFragment)
        }
        else
        {
            Toast.makeText(activity, "Please fill out all the fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun input_check(name : String, type:Int, amount:String, date: String, time: String): Boolean{
        if(type==0)
            return false

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(amount) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(time))
            return true

        return false
    }

    fun updateImage(){
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
                    binding.updateDate.editText!!.setText(date)
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
                    binding.updateTime.editText!!.setText(time)
                }
            }

            //show
            timePickerFragment.show(supportFragmentManager, "TimePickerFragment")
        }
    }

    fun cancelTransaction(){
        findNavController().navigate(R.id.action_updateTransactionScreen_to_transactionListFragment)
    }

}