package com.example.dropletbarterapp.models

class Chat(
    val owners: Pair<User, User>,
    val messages: MutableList<ChatMessage>,
    val advertisement: Advertisement
)