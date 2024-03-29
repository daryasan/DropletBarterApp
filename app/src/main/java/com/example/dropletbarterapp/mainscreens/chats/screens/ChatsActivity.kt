package com.example.dropletbarterapp.mainscreens.chats.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.ActivityChatsBinding
import com.example.dropletbarterapp.mainscreens.chats.screens.fragments.PersonalChatFragment
import com.example.dropletbarterapp.models.*
import com.example.dropletbarterapp.ui.adapters.ChatAdapter
import com.example.dropletbarterapp.ui.Navigation
import com.example.dropletbarterapp.ui.models.UICategory
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
                    ChatMessage("Пpивет!", User(), LocalDateTime.now()),
                ),
                Advertisement(
                    0,
                    null,
                    "Моя книга",
                    "Новая книжка",
                    true,
                    UICategory.getPosByCategory(Category.OTHER),
                    1
                )
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.setOnChatClickListener(object : ChatAdapter.OnChatClickListener {
            override fun onChatClick(chat: Chat) {
                disableAndHideElements()
                val fragment = PersonalChatFragment.newInstance()
                //fragment.arguments = bundle
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.chatsLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        checkVisibility()

    }

    private fun checkVisibility() {
        if (adapter.chats.isEmpty()) {
            binding.textViewEmptyChats.visibility = View.VISIBLE
        } else {
            binding.textViewEmptyChats.visibility = View.INVISIBLE
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            if (fragmentManager.backStackEntryCount == 1) {
                enableAndShowElements()
            }
        }
    }

    private fun disableAndHideElements() {
        binding.layoutChatRoot.alpha = 0f
        binding.layoutChatRoot.isEnabled = false
    }

    private fun enableAndShowElements() {
        binding.layoutChatRoot.alpha = 1f
        binding.layoutChatRoot.isEnabled = true
    }

}