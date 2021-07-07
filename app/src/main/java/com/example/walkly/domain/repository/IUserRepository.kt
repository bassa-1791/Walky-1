package com.example.walkly.domain.repository

import android.app.Activity
import com.example.walkly.domain.model.User

interface IUserRepository {
    fun signUp(activity: Activity, user: User)
}