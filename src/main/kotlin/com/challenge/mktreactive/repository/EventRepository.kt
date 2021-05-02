package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.Event
import org.springframework.data.mongodb.repository.MongoRepository

interface EventRepository : MongoRepository<Event, String>