package com.example.trakbucks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import com.example.trakbucks.databinding.FragmentSettingsScreenBinding
import com.example.trakbucks.data.TransactionViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsScreen : Fragment() {
    private var _binding : FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: TransactionViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        val items = listOf("Red", "Blue", "Green", "Yellow", "Orange", "Pink")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_dropdown, items)
        (binding.themeDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val items2 = listOf("Rupees","Dollar","Euros")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_item_dropdown, items2)
        (binding.currencyDropdown.editText as? AutoCompleteTextView)?.setAdapter(adapter2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        _binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.settingsFragment = this
    }

//    fun saveChanges()
//    {
//        Toast.makeText(activity, "Changes in settings saved!", Toast.LENGTH_SHORT).show()
//        findNavController().navigate(R.id.action_settingsScreen_to_dashboard)
//    }

}