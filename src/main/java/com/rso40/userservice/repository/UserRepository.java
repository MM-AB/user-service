package com.rso40.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rso40.userservice.model.User;

public interface UserRepository extends MongoRepository<User, String>{
    
}
