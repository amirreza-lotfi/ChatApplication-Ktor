package com.example.data

import com.example.data.model.Message

interface MessageSource {
    suspend fun getAllMessages():List<Message>
    suspend fun sendMessage(message:Message)
}