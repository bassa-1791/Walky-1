package com.example.walkly.domain.model.user

/**
 * サインアップするユーザーのクラス
 */
class User(name: String, email: String, password: String, confirmPassword: String, age: Int, gender: Int) {
    private val userName: UserName = UserName(name)
    private val email: Email = Email(email)
    private val password: Password = Password(password, confirmPassword)
    private val age: Age = Age(age)
    private val gender: Gender = Gender(gender)

    /**
     * TODO: 公開範囲の検討
     * TODO: 入力値範囲の外部ファイル化検討
     */

    fun getUserName(): String {
        return userName.getValue()
    }

    fun getEmail(): String {
        return email.getValue()
    }

    fun getPassword(): String {
        return password.getValue()
    }

    fun getAge(): Int {
        return age.getValue()
    }

    fun getGender(): String {
        return gender.getValue()
    }

    fun getData(): UserData {
        return UserData(
            getUserName(),
            getEmail(),
            getPassword(),
            getAge(),
            getGender()
        )
    }
}