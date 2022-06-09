package com.example.chatapp.adapter

import android.content.Context
import android.os.Message
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.model.ChatModel

class ChatAdapter ( val context : Context, val userList: ArrayList<ChatModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ??? {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ???, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}