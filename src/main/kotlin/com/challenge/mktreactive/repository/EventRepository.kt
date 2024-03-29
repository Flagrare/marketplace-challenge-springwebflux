package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Event
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EventRepository : ReactiveMongoRepository<Event, String>