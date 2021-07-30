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

        val name: EditText = findViewById<EditText>(R.id.name)
        val mail: EditText = findViewById<EditText>(R.id.mail)
        val pass: EditText = findViewById<EditText>(R.id.pass)
        val pass2: EditText = findViewById<EditText>(R.id.pass2)
        val age: EditText = findViewById<EditText>(R.id.age)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)


        val n1 = name.text.toString()
        val m1 = mail.text.toString()
        val p1 = pass.text.toString()
        val p2 = pass2.text.toString()
        val a1: Int
        if (age.text.toString() == "") {
            a1 = 20
        } else {
            a1 = Integer.parseInt(age.text.toString())
        }

        var g1 = 3
        val checkedId = radioGroup.checkedRadioButtonId

        if (checkedId !== -1) {
            // 選択されているラジオボタンの取得
            val radioButton =
                findViewById<View>(checkedId) as RadioButton // (Fragmentの場合は「getActivity().findViewById」にする)

            // ラジオボタンのテキストを取得
            val text = radioButton.text.toString()
            if (text=="Man"){
                g1=1
            }
            if (text=="Woman"){
                g1=2
            }
            Log.v("checked", text)
        } else {
            // 何も選択されていない場合の処理
        }



        try {
            val user = User(n1, m1, p1, p2, a1, g1)
            val repository: IUserRepository = UserRepository()
            repository.signUp(AppCompatActivity(), user)
        } catch (e: IllegalArgumentException) {
            println(e.message) // エラーメッセージが表示

        }

    }
}