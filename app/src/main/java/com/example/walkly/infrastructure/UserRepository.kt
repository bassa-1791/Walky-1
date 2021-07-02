package com.example.walkly.infrastructure

import com.example.walkly.domain.model.User
import com.example.walkly.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository: IUserRepository {
    private val auth: FirebaseAuth = Firebase.auth

    override fun signUp(user: User) {
        auth.createUserWithEmailAndPassword("example.@Gmail.com", "password")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
}