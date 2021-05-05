package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserRepository : ReactiveMongoRepository<User, String>