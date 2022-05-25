package com.amirreza.data

import com.amirreza.data.model.Message

interface MessageSource {
    suspend fun getAllMessages():List<Message>
    suspend fun sendMessage(message:Message)
}