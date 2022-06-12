package com.example.trakbucks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.trakbucks.databinding.FragmentSignUpScreenBinding
import com.example.trakbucks.data.TransactionViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [SignUpScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpScreen : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding : FragmentSignUpScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_screen, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

 }