package com.example.walkly.domain.model.user

import android.util.Patterns

class Password(password: String, confirmPassword: String) {
    companion object {
        const val MIN_LENGTH= 5
        const val MAX_LENGTH = 30
    }
    private val value: String = password

    init {
        if (password.isEmpty()) {
            throw IllegalArgumentException("パスワードは必ず入力してください。")
        }

        if (!Regex("^[A-Za-z0-9]+$").matches(password)) {
            throw IllegalArgumentException("パスワードは英数字で入力してください。")
        }

        val length = password.length
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw java.lang.IllegalArgumentException("パスワードは${MIN_LENGTH}文字以上${MAX_LENGTH}文字以下まで入力できます。")
        }

        if (password != confirmPassword) {
            throw IllegalArgumentException("入力したパスワードが一致していません。")
        }
    }
}