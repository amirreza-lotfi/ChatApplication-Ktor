package com.amirreza.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Message(
    val text:String,
    val userName:String,
    val dateCreated:Long,
    @BsonId
    val id:String = ObjectId().toString()
)

