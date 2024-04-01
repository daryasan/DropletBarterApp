//package com.example.dropletbarterapp.mainscreens.chats.screens.fragments
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.activity.OnBackPressedCallback
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.dropletbarterapp.databinding.FragmentPersonalChatBinding
//import com.example.dropletbarterapp.models.User
//
//class PersonalChatFragment : Fragment() {
//
//
//    companion object {
//        fun newInstance() = PersonalChatFragment()
//    }
//
//    private lateinit var viewModel: PersonalChatViewModel
//    private lateinit var binding: FragmentPersonalChatBinding
//    private lateinit var adapter: ChatMessagesAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        viewModel = ViewModelProvider(this)[PersonalChatViewModel::class.java]
//        binding = FragmentPersonalChatBinding.inflate(layoutInflater)
//        adapter = ChatMessagesAdapter()
//
//        binding.buttonSendMes.setOnClickListener {
//            if (binding.editTextSendMes.text != null) {
//                ChatMessagesAdapter.addMessage(
//                    binding.editTextSendMes.text.toString(),
//                    adapter,
//                    User("1")
//                )
//                binding.editTextSendMes.text = null
//                binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
//            }
//        }
//
//        binding.recyclerView.post {
//            binding.recyclerView.adapter = adapter
//        }
//        binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
//
//        // go back
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                requireActivity().onBackPressed()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//}