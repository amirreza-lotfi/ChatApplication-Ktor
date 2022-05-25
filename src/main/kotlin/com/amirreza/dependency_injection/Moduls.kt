package com.amirreza.dependency_injection

import com.amirreza.data.MessageSource
import com.amirreza.data.model.MessageSourceImpl
import com.amirreza.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val projectModules = module {
    single {
        KMongo
            .createClient()
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