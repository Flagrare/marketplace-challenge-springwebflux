package com.challenge.mktreactive.repository

import com.challenge.mktreactive.entity.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String>