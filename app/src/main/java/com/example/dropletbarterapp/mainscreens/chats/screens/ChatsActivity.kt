package com.example.dropletbarterapp.mainscreens.chats.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityChatsBinding
import com.example.dropletbarterapp.models.*
import com.example.dropletbarterapp.ui.adapters.ChatAdapter
import com.example.dropletbarterapp.utils.Navigation
import java.time.LocalDateTime

class ChatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatsBinding
    private lateinit var adapter: ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Navigation.setNavigation(this, R.id.chats)

        adapter = ChatAdapter()
        adapter.chats = listOf(
            Chat(
                Pair(User(), User()),
                mutableListOf(
                    ChatMessage("Пpивет!", User(), LocalDateTime.now(), true),
                ),
                Advertisement(
                    null, "Моя книга", "Новая книжка", true, Category.OTHER, null
                )
            )
        )

        checkVisibility()
        binding.recyclerView.adapter = adapter

    }

    private fun checkVisibility() {
        if (adapter.chats.isEmpty()) {
            binding.textViewEmptyChats.visibility = View.VISIBLE
        } else {
            binding.textViewEmptyChats.visibility = View.INVISIBLE
        }
    }

}