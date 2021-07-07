package com.example.walkly.domain.model.user

class User(name: String, email: String, password: String, confirmPassword: String) {
    private val userName: UserName = UserName(name)
    private val email: Email = Email(email)
    private val password: Password = Password(password, confirmPassword)
    private val age: Number = 100
    private val gender: Number = 1
}