package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.model.ChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

   private lateinit var chatDbRef : DatabaseReference
//   private lateinit var chatRecyclerView: RecyclerView
   private lateinit var chatAdapter: ChatAdapter
   private lateinit var messageList: ArrayList<ChatModel>

    private lateinit var senderRoom : String
    private lateinit var receiverRoom : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //untuk mendapatkan nama dan user uid dari lawan bicara
        //yang dikirimkan dari user adapter
        val getName = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uuid")
        supportActionBar!!.title = getName

        //untuk mendapatkan user uid dari user yang menggunakan
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid


        chatDbRef = FirebaseDatabase.getInstance().reference
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        messageList = ArrayList()
        chatAdapter = ChatAdapter(this,messageList)

        rvIsiChat.layoutManager = LinearLayoutManager(this)
        rvIsiChat.adapter = chatAdapter


        //logic for adding data to recyclerView
        chatDbRef.child("chats").child(senderRoom!!).
        child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(ChatModel::class.java)
                        messageList.add(message!!)
                    }
                    chatAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        //adding message to database
        btnSendChat.setOnClickListener {
            val message = etInputTextChat.text.toString()
            val messageObject = ChatModel(message,senderUid)

            chatDbRef.child("chats").child(senderRoom!!).child("messages")
                .push().setValue(messageObject).addOnSuccessListener {
                    chatDbRef.child("chats").child(receiverRoom!!).child("messages")
                        .push().setValue(messageObject)
                   }
            etInputTextChat.text.clear()
        }

    }
}