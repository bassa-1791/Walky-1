package com.example.walkly.infrastructure

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.walkly.domain.model.MyApplication
import com.example.walkly.domain.model.User
import com.example.walkly.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class UserRepository: IUserRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    override fun signUp(activity: Activity, user: User) {
        auth.createUserWithEmailAndPassword("example2@Gmail.com", "password")
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val currentUser = auth.currentUser

                    val user = hashMapOf(
                        "user_name" to "のび太",
                        "age" to 10,
                        "gender" to 1

                    )
                    db.collection("users")
                        .document(currentUser?.uid.toString())
                        .set(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }





    }
}