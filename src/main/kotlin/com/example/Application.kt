package com.example

import io.ktor.application.*
import com.example.plugins.*
import org.koin.ktor.ext.Koin
import com.example.dependency_injection.projectModules

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)


@Suppress("unused")
fun Application.module() {

    install(Koin){
        modules(projectModules)
    }
    configureSockets()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
}
