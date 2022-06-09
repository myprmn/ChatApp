package com.example.chatapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.ChatActivity
import com.example.chatapp.R
import com.example.chatapp.model.UserModel

class UserAdapter(val userList: ArrayList<UserModel>): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName : TextView = itemView.findViewById(R.id.tvNamaFrag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_main_menu,parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList [position]
        holder.tvName.text = currentUser.name

        holder.itemView.setOnClickListener {
            val intent = Intent (holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uuid", currentUser.uuid)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = userList.size

}