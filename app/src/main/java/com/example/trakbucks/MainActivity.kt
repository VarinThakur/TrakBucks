package com.example.trakbucks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setTheme(R.style.Blue)


        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val check = sharedPreferences.getString("signOut","false")

        if(check == "false")
        {
            val intent = Intent(this, SignUpActivity::class.java).apply {
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            setContentView(R.layout.activity_main)
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
