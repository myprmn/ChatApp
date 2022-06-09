package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.adapter.UserAdapter
import com.example.chatapp.model.UserModel
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<UserModel>
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        userRecyclerView = findViewById(R.id.rvListFriend)
        userRecyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        userRecyclerView.setHasFixedSize(true)

        userList = ArrayList()
        getUser()
    }

    private fun getUser() {
        mDbRef = FirebaseDatabase.getInstance().getReference("User")

        mDbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userList.clear()
                    for (listDataUser in snapshot.children){
                        val listUser = listDataUser.getValue(UserModel::class.java)
                        userList.add(listUser!!)
                    }
                    userRecyclerView.adapter = UserAdapter(userList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}