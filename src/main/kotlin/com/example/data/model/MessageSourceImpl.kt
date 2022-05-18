package com.example.data.model

import com.example.data.MessageSource
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageSourceImpl(
    database:CoroutineDatabase
):MessageSource {
    val messages = database.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find()
            .descendingSort(Message::dateCreated)
            .toList()
    }

    override suspend fun sendMessage(message:Message) {
        messages.insertOne(message)
    }
}