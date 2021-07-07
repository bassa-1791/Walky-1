package com.example.walkly.domain.model.user

import android.util.Patterns
import java.lang.IllegalArgumentException

class Email(email: String) {
    private val value: String =  email

    init {
        if (email.isEmpty()) {
            throw IllegalArgumentException("メールアドレスは必ず入力してください。")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw IllegalArgumentException("メールアドレスの形式が誤っています。")
        }
    }
}