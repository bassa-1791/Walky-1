package com.example.walkly

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.application.UserApplicationService
import com.example.walkly.domain.model.User
import com.example.walkly.domain.repository.IUserRepository
import com.example.walkly.infrastructure.UserRepository

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private val userApplication: UserApplicationService= UserApplicationService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val button: Button = findViewById<Button>(R.id.Activity_Button)
        button.setOnClickListener(this)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

    }
    override fun onClick(v: View?) {

        try {
            val user = User("user", "example@example.com", "password", "password", 20, 1)
            val data = user.getData()
            print(data.gender) // 男性
        } catch (e: IllegalArgumentExcception) {
            println(e.message) // エラーメッセージが表示
        }
        //あとで消すよ
        val repository: IUserRepository = UserRepository()
        repository.signUp(AppCompatActivity(), "username","email","password",1,1)
    }

}