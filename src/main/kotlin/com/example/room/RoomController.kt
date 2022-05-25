package com.example.room

import com.example.data.MessageSource
import com.example.data.model.Message
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageSource: MessageSource
) {
    /**
     * String -> session id of member.
     * Member -> The member that already exist in room.
    * */

    private val membersOfChatroom = ConcurrentHashMap<String, Member>()

    fun onJoined(
        userName:String,
        sessionId:String,
        socket:WebSocketSession
    ){
        if(membersOfChatroom.contains(userName)){
            //todo
        }
        else{
            membersOfChatroom[userName] = Member(userName,sessionId,socket)
        }
    }

    suspend fun broadcastMessage(
        message:String,
        messageSender:String
    ){
        val messageObject = Message(
            text = message,
            userName = messageSender,
            dateCreated = System.currentTimeMillis()
        )

        messageSource.sendMessage(messageObject)
        val jsonString = Json.encodeToString(message)

        membersOfChatroom.values.forEach{ member ->
            member.socket.send(Frame.Text(jsonString))
        }
    }

    suspend fun getAllMessages():List<Message>{
        return messageSource.getAllMessages()
    }

    suspend fun disconnectUser(userName: String){
        membersOfChatroom[userName]?.socket?.close()
        removeUserName(userName)
    }

    private fun removeUserName(userName: String){
        membersOfChatroom.remove(userName)
    }

}