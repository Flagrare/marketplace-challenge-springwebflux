package com.challenge.mktreactive.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*


abstract class Event {
    abstract val id: String
    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    abstract val eventDateTime: LocalDateTime
    abstract val user: User
    abstract val action: String
}

@Document(collection = "event")
data class CrudEvent(
    @Id
    override val id: String = UUID.randomUUID().toString(),
    @Indexed
    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    override val eventDateTime: LocalDateTime = LocalDateTime.now(),
    @Indexed
    override val user: User,
    @Indexed
    override val action: String,
    val entity: Any?,
    @Indexed
    val entityClass: String? = null
) : Event()

@Document(collection = "event")
data class ShareEvent(
    @Id
    override val id: String = UUID.randomUUID().toString(),
    @Indexed
    @get:JsonFormat(shape = JsonFormat.Shape.STRING)
    override val eventDateTime: LocalDateTime = LocalDateTime.now(),
    @Indexed
    override val user: User,
    @Indexed
    override val action: String = "share",
    val resource: Any?,
    @Indexed
    val resourceClass: String? = null,
    val agents: List<Any> = mutableListOf()
) : Event()

//@Document(collection = "event")
//data class EventAudit(
//        val entity: Any?,
//        val entityClass: String? = null,
//        val user: UserVO,
//)