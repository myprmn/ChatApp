package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.chatapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    lateinit var dbSignUp : DatabaseReference

    lateinit var btnSignIn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        dbSignUp = FirebaseDatabase.getInstance().reference
        btnSignIn = findViewById(R.id.SignInOnSignUp)

        btnSignUp.setOnClickListener {
            val name = etNamaLengkapSignUp.text.toString()
            val email = etEmailSignUp.text.toString()
            val password = etPasswordSignUp.text.toString()

            if (name !="" && email !="" && password!=""){
                signUp(email,password)
            } else {
                Toast.makeText(this,"Masih ada field yang kosong", Toast.LENGTH_SHORT).show()
            }
        }
        btnSignIn.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){task ->
                if (task.isComplete){
                    val name = etNamaLengkapSignUp.text.toString()
                    val email = etPasswordSignUp.text.toString()
                    val uuid = mAuth.uid.toString()

                    dbSignUp.child("User").child((uuid))
                        .setValue(UserModel(name,email,uuid))
                    Toast.makeText(this,"Sign Up Berhasil", Toast.LENGTH_SHORT).show()
                    etEmailSignUp.text.clear()
                    etNamaLengkapSignUp.text.clear()
                    etPasswordSignUp.text.clear()
                } else {
                    Toast.makeText(this,"Sign Up Gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }
}