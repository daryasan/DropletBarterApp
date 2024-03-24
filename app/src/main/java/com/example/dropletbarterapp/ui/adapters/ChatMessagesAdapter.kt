package com.example.dropletbarterapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dropletbarterapp.databinding.MessageRecievedBinding
import com.example.dropletbarterapp.databinding.MessageSentBinding
import com.example.dropletbarterapp.models.ChatMessage
import com.example.dropletbarterapp.models.User
import java.time.LocalDateTime

class ChatMessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MESSAGE_SENT = 1;
    private val MESSAGE_RECEIVED = 2;


    var messages: MutableList<ChatMessage> = mutableListOf(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == MESSAGE_RECEIVED) {
            val binding = MessageRecievedBinding.inflate(inflater, parent, false)
            ReceivedMessageViewHolder(binding)
        } else {
            val binding = MessageSentBinding.inflate(inflater, parent, false)
            SentMessageViewHolder(binding)
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        // TODO get current user
        return if (messages[position].sender.email == "1") {
            MESSAGE_SENT
        } else {
            MESSAGE_RECEIVED
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        if (getItemViewType(position) == MESSAGE_RECEIVED) {
            (holder as ReceivedMessageViewHolder).bind(message)
        } else {
            (holder as SentMessageViewHolder).bind(message)
        }

    }

    class SentMessageViewHolder(val binding: MessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {

            binding.textViewRecievedMes.text = message.message
            val time = "${message.sendingTime.hour}:${message.sendingTime.minute}"
            binding.textViewTime.text = time
        }

    }

    class ReceivedMessageViewHolder(val binding: MessageRecievedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.textViewRecievedMes.text = message.message
            val time = "${message.sendingTime.hour}:${message.sendingTime.minute}"
            binding.textViewTime.text = time
        }

    }

    companion object {
        fun addMessage(message: String, adapter: ChatMessagesAdapter, sender: User) {
            adapter.messages.add(ChatMessage(message, sender, LocalDateTime.now()))
            adapter.notifyDataSetChanged()
        }
    }

}