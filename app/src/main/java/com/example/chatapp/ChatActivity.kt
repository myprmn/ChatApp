package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.model.ChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

   private lateinit var chatDbRef : DatabaseReference

   private lateinit var senderRoom : String
   private lateinit var receiverRoom : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val getName = intent.getStringExtra("name")
        supportActionBar!!.title = getName
        val receiverUid = intent.getStringExtra("uuid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        chatDbRef = FirebaseDatabase.getInstance().reference
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        btnSendChat.setOnClickListener {
            val message = etInputTextChat.text.toString()
            val messageObject = ChatModel(message,senderUid)

            chatDbRef.child("chats").child(senderRoom!!).child("messages")
                .push().setValue(messageObject).addOnSuccessListener {
                    chatDbRef.child("chats").child(receiverRoom!!).child("messages")
                        .push().setValue(messageObject)
                    etInputTextChat.text.clear()
                }
        }

    }
}