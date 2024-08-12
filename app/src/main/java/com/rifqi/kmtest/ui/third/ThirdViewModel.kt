package com.rifqi.kmtest.ui.third

import androidx.lifecycle.ViewModel
import com.rifqi.kmtest.data.repository.UsersRepository

class ThirdViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    val users = usersRepository.getUsers()
}