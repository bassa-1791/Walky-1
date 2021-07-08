package com.example.walkly.domain.model.user

/**
 * 性別
 */
class Gender(gender: Int) {
    companion object {
        const val MIN_VALUE = 1
        const val MAX_VALUE = 3
    }
    private val value: Int = gender

    init {
        if (gender < MIN_VALUE || gender > MAX_VALUE) {
            throw IllegalArgumentException("不正な入力値です。管理者に連絡してください。")
        }
    }

    fun getValue(): String {
        if (value == 1) {
            return "男性"
        }
        if (value == 2) {
            return "女性"
        }
        return "回答なし"
    }
}