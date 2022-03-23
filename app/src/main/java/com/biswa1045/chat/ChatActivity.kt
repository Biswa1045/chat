package com.biswa1045.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var chat_rec: RecyclerView
    private lateinit var msg: EditText
    private lateinit var send: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mref:DatabaseReference
    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
   //     val intent = Intent()
        val bundle:Bundle?=intent.extras

        val name = bundle?.getString("name").toString()
        val receiveruid =  bundle?.getString("uid").toString()
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        supportActionBar?.title = name

        mref = FirebaseDatabase.getInstance().getReference()
        senderRoom = receiveruid+  senderuid
        receiverRoom = senderuid+  receiveruid

        chat_rec = findViewById(R.id.chat_rec)
        msg = findViewById(R.id.chat_msg)
        send = findViewById(R.id.send_msg_img)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        chat_rec.layoutManager = LinearLayoutManager(this)
        chat_rec.adapter = messageAdapter


        //add data to recycler view
        mref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postsnapshot in snapshot.children){
                        val message = postsnapshot.getValue(Message::class.java)
                        messageList.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        send.setOnClickListener{
            val message = msg.text.toString()
            if(!message.equals("")){
                val messageObject = Message(message,senderuid)
                mref.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mref.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(messageObject)
                    }
                msg.setText("")
            }else{

            }

        }

    }
}