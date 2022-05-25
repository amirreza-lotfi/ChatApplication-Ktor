package com.amirreza.data.model

import com.amirreza.data.MessageSource
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageSourceImpl(
    database:CoroutineDatabase
):MessageSource {
    private val messages = database.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find()
            .descendingSort(Message::dateCreated)
            .toList()
    }

    override suspend fun sendMessage(message:Message) {
        messages.insertOne(message)
    }
}