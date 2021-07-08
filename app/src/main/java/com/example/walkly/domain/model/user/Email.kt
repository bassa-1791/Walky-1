package com.example.walkly.domain.model.user

import android.util.Patterns

/**
 * メールアドレス
 */
class Email(email: String) {
    private val value: String =  email

    init {
        if (email.isEmpty()) {
            throw IllegalArgumentException("メールアドレスを入力してください。")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw IllegalArgumentException("メールアドレスの形式が誤っています。")
        }
    }

    fun getValue(): String {
        return value
    }
}