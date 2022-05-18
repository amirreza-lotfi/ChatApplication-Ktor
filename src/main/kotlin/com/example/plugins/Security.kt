package com.example.plugins

import io.ktor.sessions.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.util.*
import javax.print.attribute.standard.RequestingUserName

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(ApplicationCallPipeline.Features){
        if(call.sessions.get<ChatSession>() == null){
            val username = call.parameters["username"] ?: "Guest"
            call.sessions.set(
                ChatSession(username, generateNonce())
            )
        }
    }
}

data class ChatSession(
    val userName: String,
    val sessionId:String
)
