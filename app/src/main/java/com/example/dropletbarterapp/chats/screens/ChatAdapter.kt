package com.example.dropletbarterapp.chats.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ChatItemBinding
import com.example.dropletbarterapp.models.Chat

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatsViewHolder>() {

    var chats: List<Chat> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class ChatsViewHolder(val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatItemBinding.inflate(inflater, parent, false)
        return ChatsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val chat = chats[position]
        val context = holder.itemView.context

        with(holder.binding) {

            if (chat.advertisement.photo != null) {
                Glide.with(context).load(chat.advertisement.photo).circleCrop()
                    .error(R.drawable.empty_profile_image).circleCrop()
                    .placeholder(R.drawable.empty_profile_image).circleCrop().into(imageViewChat)
            }

            textViewChatName.text = chat.advertisement.name
            textViewRecentMessage.text = chat.messages[chat.messages.lastIndex].message
        }

    }
}