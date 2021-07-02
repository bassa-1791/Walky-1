package com.example.walkly.domain.repository

import com.example.walkly.domain.model.User

interface IUserRepository {
    fun signUp(user: User)
}