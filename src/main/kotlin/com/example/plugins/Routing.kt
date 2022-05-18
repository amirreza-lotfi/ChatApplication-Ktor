package com.example.plugins

import com.example.room.RoomController
import com.example.rout.chatRoutes
import com.example.rout.getAllMessages
import io.ktor.routing.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()

    install(Routing){
        chatRoutes(roomController)
        getAllMessages(roomController)
    }
}
