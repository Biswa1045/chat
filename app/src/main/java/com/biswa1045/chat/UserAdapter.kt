package com.biswa1045.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class UserAdapter(val context: Context, val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.user_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textname.text = currentUser.name
        holder.itemView.setOnClickListener{

            val intent5 = Intent(context,ChatActivity::class.java)
            intent5.putExtra("name",currentUser.name)
            intent5.putExtra("uid",currentUser.uid)

            context.startActivity(intent5)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textname = itemView.findViewById<TextView>(R.id.txt_name);
    }
}


