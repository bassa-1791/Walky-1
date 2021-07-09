package com.example.walkly.domain.model.user

/**
 * 年齢
 */
class Age(age: Int) {
    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 120
    }
    private val value: Int = age

    init {
        if (age < MIN_VALUE || age > MAX_VALUE) {
            throw IllegalArgumentException("年齢は${MIN_VALUE}歳から${MAX_VALUE}歳までの範囲で入力してください。")
        }
    }

    fun getValue(): Int {
        return value
    }
}