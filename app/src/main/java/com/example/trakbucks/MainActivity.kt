package com.example.trakbucks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.example.trakbucks.data.TransactionApplication
import com.example.trakbucks.data.TransactionViewModel
import com.example.trakbucks.data.TransactionViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent?.extras?.getString("Name").toString()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val theme = sharedPreferences.getString("Theme","Red")

        when(theme)
        {
            "Red"->{setTheme((R.style.Red))}
            "Blue"->{setTheme(R.style.Blue)}
            "Green"->{setTheme(R.style.Green)}
        }

        val check = sharedPreferences.getString("signOut","false")

        if(check == "false")
        {
            val intent = Intent(this, SignUpActivity::class.java).apply {
            }
            startActivity(intent)
            finish()
        }
        else {
            setContentView(R.layout.activity_main)
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            setupWithNavController(bottomNavigationView, navController)

            bottomNavigationView.setOnItemReselectedListener { item ->
                // Pop everything up to the reselected item
                val reselectedDestinationId = item.itemId
                navController.popBackStack(reselectedDestinationId, inclusive = false)
            }


//        bottomNavigationView.setOnItemSelectedListener { item ->
//            val selectedDestinationId = item.itemId
//            navController.popBackStack(selectedDestinationId, inclusive = false)
//
//        }

            bottomNavigationView.setOnItemReselectedListener { item ->
                // Pop everything up to the reselected item
                val reselectedDestinationId = item.itemId
                navController.popBackStack(reselectedDestinationId, inclusive = false)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
