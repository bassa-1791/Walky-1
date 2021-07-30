package com.example.walkly

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.walkly.application.UserApplicationService
import com.example.walkly.domain.model.user.User
import com.example.walkly.domain.repository.IUserRepository
import com.example.walkly.infrastructure.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private val userApplication: UserApplicationService = UserApplicationService(this)
    lateinit var auth: FirebaseAuth
    var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val button: Button = findViewById<Button>(R.id.Activity_Button)
        button.setOnClickListener(this)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


    }

    override fun onClick(v: View?) {

        val username: EditText = findViewById<EditText>(R.id.name)
        val mailaddress: EditText = findViewById<EditText>(R.id.mail)
        val password: EditText = findViewById<EditText>(R.id.pass)
        val password2: EditText = findViewById<EditText>(R.id.pass2)
        val userage: EditText = findViewById<EditText>(R.id.age)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)


        val name = username.text.toString()
        val mail = mailaddress.text.toString()
        val pass = password.text.toString()
        val pass2 = password2.text.toString()
        val age: Int
        if (userage.text.toString() == "") {
            age = 20
        } else {
            age = Integer.parseInt(userage.text.toString())
        }

        var gender = 3
        val checkedId = radioGroup.checkedRadioButtonId

        if (checkedId !== -1) {
            // 選択されているラジオボタンの取得
            val radioButton =
                findViewById<View>(checkedId) as RadioButton // (Fragmentの場合は「getActivity().findViewById」にする)

            // ラジオボタンのテキストを取得
            val text = radioButton.text.toString()
            if (text=="Man"){
                gender=1
            }
            if (text=="Woman"){
                gender=2
            }
            Log.v("checked", text)
        } else {
            // 何も選択されていない場合の処理
        }



        try {
            val user = User(name, mail, pass, pass2, age, gender)
            val repository: IUserRepository = UserRepository()
            repository.signUp(AppCompatActivity(), user)
        } catch (e: IllegalArgumentException) {
            println(e.message) // エラーメッセージが表示

        }

    }
}