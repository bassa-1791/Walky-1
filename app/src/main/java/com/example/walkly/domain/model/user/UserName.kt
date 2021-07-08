package com.example.walkly.domain.model.user

/**
 * ユーザー名
 */
class UserName(name: String) {
    companion object {
        const val MIN_LENGTH= 3
        const val MAX_LENGTH = 10
    }
    private val value: String = name

    init {
        if (name.isEmpty()) {
            throw IllegalArgumentException("ユーザ名を入力してください。")
        }
        val length = name.length
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            throw IllegalArgumentException("ユーザー名は${MIN_LENGTH}文字以上${MAX_LENGTH}文字以下まで入力できます。")
        }
    }

    fun getValue(): String {
        return value
    }
}