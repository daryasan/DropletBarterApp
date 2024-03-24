package com.example.dropletbarterapp.mainscreens.chats.screens.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import antlr.debug.MessageAdapter
import com.example.dropletbarterapp.databinding.FragmentPersonalChatBinding
import com.example.dropletbarterapp.models.ChatMessage
import com.example.dropletbarterapp.models.User
import com.example.dropletbarterapp.ui.adapters.ChatAdapter
import com.example.dropletbarterapp.ui.adapters.ChatMessagesAdapter

class PersonalChatFragment : Fragment() {


    companion object {
        fun newInstance() = PersonalChatFragment()
    }

    private lateinit var viewModel: PersonalChatViewModel
    private lateinit var binding: FragmentPersonalChatBinding
    private lateinit var adapter: ChatMessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[PersonalChatViewModel::class.java]
        binding = FragmentPersonalChatBinding.inflate(layoutInflater)
        adapter = ChatMessagesAdapter()

        binding.buttonSendMes.setOnClickListener {
            if (binding.editTextSendMes.text != null) {
                ChatMessagesAdapter.addMessage(
                    binding.editTextSendMes.text.toString(),
                    adapter,
                    User("1")
                )
                binding.editTextSendMes.text = null
                binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        }

        binding.recyclerView.post {
            binding.recyclerView.adapter = adapter
        }
        binding.recyclerView.scrollToPosition(adapter.itemCount - 1)

        // go back
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = requireActivity().supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    if (fragmentManager.backStackEntryCount == 1) {
                        requireActivity().onBackPressed()
                    }
                    fragmentManager.popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}