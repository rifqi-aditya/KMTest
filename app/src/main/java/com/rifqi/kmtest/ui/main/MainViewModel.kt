package com.rifqi.kmtest.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    fun isPalindrome(sentence: String): Boolean {
        val cleanedSentence = sentence.replace(Regex("[^A-Za-z]"), "").lowercase()
        val reversedSentence = cleanedSentence.reversed()
        return cleanedSentence == reversedSentence
    }
}