package com.example.dropletbarterapp.mainscreens.chats.screens.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dropletbarterapp.R

class PersonalChatFragment : Fragment() {

    companion object {
        fun newInstance() = PersonalChatFragment()
    }

    private lateinit var viewModel: PersonalChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_chat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonalChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}