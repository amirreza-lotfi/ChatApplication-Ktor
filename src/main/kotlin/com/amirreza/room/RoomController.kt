package com.amirreza.room

import com.amirreza.data.MessageSource
import com.amirreza.data.model.Message
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

    fun onJoinedChat(
        userName:String,
        sessionId:String,
        socket:WebSocketSession
    ){
        if(membersOfChatroom.contains(userName)){
            //todo : implement your plan when there is same name in chatroom.
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

        sendMessageToOtherUsersInChat(jsonString)
    }

    private suspend fun sendMessageToOtherUsersInChat(jsonString:String){
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