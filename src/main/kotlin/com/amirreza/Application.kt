package com.amirreza

import io.ktor.application.*
import com.amirreza.plugins.*
import org.koin.ktor.ext.Koin
import com.amirreza.dependency_injection.projectModules

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
