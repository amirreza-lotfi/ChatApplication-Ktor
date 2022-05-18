package com.example.dependency_injection

import com.example.data.MessageSource
import com.example.data.model.MessageSourceImpl
import com.example.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val projectModules = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("chatroom_db")
    }
    single<MessageSource>{
        MessageSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}