package com.example.trakbucks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.preference.PreferenceManager
import com.example.trakbucks.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.goButton.setOnClickListener{
            if(binding.name.editText?.text.toString() == "" )
            {
                binding.name.error = "Name can't be empty."
                Toast.makeText(this,"Enter your Name to proceed.",LENGTH_SHORT).show()
            }
            else
            {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
                with (sharedPreferences.edit()) {
                    putString("signOut","true")
                    apply()
                }
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                startActivity(intent)
                finish()
            }
        }
    }


}