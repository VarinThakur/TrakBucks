package com.example.trakbucks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.databinding.FragmentAddTransactionBinding
import com.example.trakbucks.databinding.FragmentDashboardBinding
import com.example.trakbucks.databinding.FragmentSettingsScreenBinding

class AddTransactionScreen : Fragment() {
    private var _binding : FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

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
        Toast.makeText(activity, "Added Transaction successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_addTransactionScreen_to_transactionListFragment)
    }

    fun addImage(){
        Toast.makeText(activity, "Added image successfully", Toast.LENGTH_SHORT).show()
    }
}