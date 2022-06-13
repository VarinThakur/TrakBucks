package com.example.trakbucks

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.trakbucks.data.TransactionViewModel


class SettingsFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {

    private lateinit var myTransactionViewModel: TransactionViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.settings, rootKey)
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)

        val examplePreference: Preference? = findPreference("signOut")
        examplePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {

            myTransactionViewModel= ViewModelProvider(this).get(TransactionViewModel::class.java)
            myTransactionViewModel.deleteAllTransactions()

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext() /* Activity context */)
            with(sharedPreferences?.edit())
            {
                this?.putString("signOut","false")
                this?.apply()
            }
            activity?.recreate()
            return@OnPreferenceClickListener true
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key == "Theme")
        {
            val prefs = sharedPreferences?.getString(key,"Red")
            when(prefs)
            {
                "Red"-> {Toast.makeText(activity,"Red Theme",LENGTH_SHORT).show()}
                "Blue"-> {Toast.makeText(activity,"Blue Theme",LENGTH_SHORT).show()}
                "Green"-> {Toast.makeText(activity,"Green Theme",LENGTH_SHORT).show()}
            }
            activity?.recreate()
        }
        if(key == "Currency")
        {
            val prefs = sharedPreferences?.getString(key,"INR")
            when(prefs)
            {
                "INR"-> {Toast.makeText(activity,"Set to Indian Rupee",LENGTH_SHORT).show()}
                "DOL"-> {Toast.makeText(activity,"Set to American Dollar",LENGTH_SHORT).show()}
                "EUR"-> {Toast.makeText(activity,"Set to Indian Rupee",LENGTH_SHORT).show()}
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
}