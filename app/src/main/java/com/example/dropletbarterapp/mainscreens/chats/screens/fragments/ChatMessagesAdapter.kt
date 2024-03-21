package com.example.dropletbarterapp.mainscreens.chats.screens.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.MessageRecievedBinding
import com.example.dropletbarterapp.models.ChatMessage
import com.example.dropletbarterapp.models.User
import java.time.LocalDateTime

class ChatMessagesAdapter : RecyclerView.Adapter<ChatMessagesAdapter.MessageViewHolder>() {

    var messages: List<ChatMessage> = listOf(
        ChatMessage("Короткое сообщение", User("1"), LocalDateTime.now()),
        ChatMessage("Короткое сообщение", User("2"), LocalDateTime.now()),
        ChatMessage(
            "Длинное сообщение сообщение сообщение сообщение сообщение " +
                    "сообщение сообщение сообщениесообщениесообщение",
            User("1"),
            LocalDateTime.now()
        ),
        ChatMessage("Короткое сообщение", User("1"), LocalDateTime.now()),
        ChatMessage(
            "Длинное сообщение сообщениесообщение сообщение сообщение " +
                    "сообщение сообщение сообщение сообщение сообщение сообщение сообщение " +
                    "сообщение сообщение сообщение сообщение", User("2"), LocalDateTime.now()
        ),
    )
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class MessageViewHolder(val binding: MessageRecievedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MessageRecievedBinding.inflate(inflater, parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        with(holder.binding) {

            if (message.sender.email == "1") {


            } else {

            }

        }

    }
}