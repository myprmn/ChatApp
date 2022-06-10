package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class Login : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var dbSignIn: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        dbSignIn = FirebaseDatabase.getInstance().reference

        tvSignUpOnSignIn.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        btnSignIn.setOnClickListener {
            val email = etEmailSignIn.text.toString()
            val password = etPasswordSignIn.text.toString()
            if (email != "" && password != "") {
                DataLogin(email, password)
            } else {
                Toast.makeText(this, "Field masih kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun DataLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isComplete) {
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
