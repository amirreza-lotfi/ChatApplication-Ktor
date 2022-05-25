package com.amirreza.rout

import com.amirreza.plugins.ChatSession
import com.amirreza.room.RoomController
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatRoutes(roomController: RoomController){
    webSocket("/chatroom-websockets") {
        val session = call.sessions.get<ChatSession>()

        if(session == null){
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY,"No session."))
            return@webSocket
        }

        try{
            roomController.onJoinedChat(session.userName,session.sessionId,this)
            incoming.consumeEach {
                if(it is Frame.Text){
                    roomController.broadcastMessage(it.readText(), session.userName)
                }
            }
        }catch(e:Exception){
            e.printStackTrace()
        }finally {
            roomController.disconnectUser(session.userName)
        }
    }
}