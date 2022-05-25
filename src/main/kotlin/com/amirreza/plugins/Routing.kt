package com.amirreza.plugins

import com.amirreza.room.RoomController
import com.amirreza.rout.chatRoutes
import com.amirreza.rout.getAllMessages
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
