package com.rifqi.kmtest.ui.second

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rifqi.kmtest.databinding.ActivitySecondScreenBinding
import com.rifqi.kmtest.ui.third.ThirdScreenActivity

class SecondScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySecondScreenBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedUserName = intent.getStringExtra("SELECTED_USER_NAME")
        binding.tvSelectedUserName.text = selectedUserName
        restoreSavedName()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnChooseUser.setOnClickListener {
            startActivity(Intent(this, ThirdScreenActivity::class.java))
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun restoreSavedName() {
        val savedName = sharedPreferences.getString("USER_NAME", null)
        if (savedName != null) {
            binding.tvName.text = savedName
        }
    }
}