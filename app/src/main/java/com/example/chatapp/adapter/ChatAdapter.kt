package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ChatActivity
import com.example.chatapp.R
import com.example.chatapp.model.ChatModel
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(val context: ChatActivity, val messageList: ArrayList<ChatModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1) {
            //inflate receive
            val view : View = LayoutInflater.from(context).inflate(R.layout.fragment_receive,parent, false)
            return receiveViewHolder(view)
        } else {
            //inflate sent
            val view : View = LayoutInflater.from(context)
                .inflate(R.layout.fragment_send,parent, false)
            return sentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if(holder.javaClass == sentViewHolder::class.java){
            //do the stuff for sent view holder
            val viewHolder = holder as sentViewHolder
            holder.sentMessage.text = currentMessage.message

        } else {
            //do stuff for receive view holder
            val viewHolder = holder as receiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderUid)){
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    class sentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.tvSentFrag)

    }
    class receiveViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage = itemView.findViewById<TextView>(R.id.tvReceiveFrag)
    }

}