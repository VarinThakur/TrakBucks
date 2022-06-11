package com.example.trakbucks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.trakbucks.databinding.FragmentDashboardBinding
import com.example.trakbucks.databinding.FragmentSettingsScreenBinding
import com.example.trakbucks.model.TransactionViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

    fun saveChanges()
    {
        Toast.makeText(activity, "Changes in settings saved!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_settingsScreen_to_dashboard)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}