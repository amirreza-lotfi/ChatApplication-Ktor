package com.example.rout

import com.example.plugins.ChatSession
import com.example.room.RoomController
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
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
            roomController.onJoined(session.userName,session.sessionId,this)
            incoming.consumeEach {
                if(it is Frame.Text){
                    roomController.broadcastMessage(it.readText(), session.userName)
                }
            }
//        }catch (e:MemberExistInRoomException){
//            //call.respond(HttpStatusCode.Conflict)
        }catch(e:Exception){
            e.printStackTrace()
        }finally {
            roomController.disconnectUser(session.userName)
        }
    }
}