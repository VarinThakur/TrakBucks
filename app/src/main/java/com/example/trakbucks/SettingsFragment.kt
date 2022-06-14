package com.example.trakbucks

import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory
import kotlin.properties.Delegates


class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {

//    var myTransactionViewModel2: TransactionViewModel by Delegates.notNull<TransactionViewModel>()
//    private val myTransactionViewModel: TransactionViewModel by activityViewModels {
//        TransactionViewModelFactory(
//            (requireActivity().application as TransactionApplication).database
//                .transactionDao()
//        )
//    }

    private val myTransactionViewModel: TransactionViewModel by activityViewModels {
        TransactionViewModelFactory(
            (activity?.application as TransactionApplication).database
                .transactionDao()
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.settings, rootKey)
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)

        //myTransactionViewModel2 = assignViewModel()

        val examplePreference: Preference? = findPreference("signOut")
        examplePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {

            myTransactionViewModel.deleteUserDetails()
            myTransactionViewModel.deleteAllTransactions()


            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(requireContext() /* Activity context */)
            reset(sharedPreferences)
            val intent = Intent(requireContext(), MainActivity::class.java).apply {
            }
            startActivity(intent)
            requireActivity().finish()
            return@OnPreferenceClickListener true
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "Theme") {
            val prefs = sharedPreferences?.getString(key, "Red")
            applyTheme(sharedPreferences,prefs!!)
            requireActivity().recreate()
        }
        if (key == "Currency") {
            val prefs = sharedPreferences?.getString(key, "INR")
            when (prefs) {
                "INR" -> {
                    Toast.makeText(activity, "Set to Indian Rupee", LENGTH_SHORT).show()

                }
                "DOL" -> {
                    Toast.makeText(activity, "Set to American Dollar", LENGTH_SHORT).show()
                }
                "EUR" -> {
                    Toast.makeText(activity, "Set to Indian Rupee", LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Frag", "Fragment Destroyed!")
    }

    private fun reset(sharedPreferences: SharedPreferences?){
        with(sharedPreferences?.edit())
        {
            this?.putString("signOut", "false")
            this?.putString("Currency","INR")
            this?.putString("Theme","Red")
            this?.apply()
        }
    }

    private fun applyTheme(sharedPreferences: SharedPreferences?, theme : String ){
        with(sharedPreferences?.edit())
        {
            this?.putString("Theme",theme)
            this?.apply()
        }
    }

//    private fun assignViewModel() : TransactionViewModel {
//        val myTransactionViewModel2: TransactionViewModel by activityViewModels {
//            TransactionViewModelFactory(
//
//                (requireActivity().application as TransactionApplication).database
//                    .transactionDao()
//            )
//        }
//        return myTransactionViewModel2
//    }

}