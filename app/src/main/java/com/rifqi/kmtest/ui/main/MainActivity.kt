package com.rifqi.kmtest.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rifqi.kmtest.databinding.ActivityMainBinding
import com.rifqi.kmtest.ui.second.SecondScreenActivity
import com.saadahmedev.popupdialog.PopupDialog

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnCheck.setOnClickListener {
            handleCheckButtonClick()
        }

        binding.btnNext.setOnClickListener {
            handleNextButtonClick()
        }
    }

    private fun checkPalindrome(text: String) {
        val isPalindrome = viewModel.isPalindrome(text)
        showPalindromeDialog(isPalindrome)
    }

    private fun handleNextButtonClick() {
        val name = binding.etName.text.toString().trim()

        if (!TextUtils.isEmpty(name)) {
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            saveName(name)
            val intent = Intent(this, SecondScreenActivity::class.java)
            startActivity(intent)
        } else {
            binding.etName.error = "Name is required"
        }
    }

    private fun handleCheckButtonClick() {
        val palindrome = binding.etPalindrome.text.toString()

        if (!TextUtils.isEmpty(palindrome)) {
            checkPalindrome(palindrome)
        } else {
            binding.etPalindrome.error = "Sentence is required"
        }
    }

    private fun showPalindromeDialog(isPalindrome: Boolean) {
        if (isPalindrome) {
            PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("isPalindrome")
                .setDescription("The sentence is a palindrome")
                .build(Dialog::dismiss)
                .show()
        } else {
            PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("not palindrome")
                .setDescription("The sentence is not a palindrome. Try again.")
                .build(Dialog::dismiss)
                .show()
        }
    }

    private fun saveName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USER_NAME", name)
        editor.apply()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}