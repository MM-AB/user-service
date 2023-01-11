package com.rso40.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rso40.userservice.model.User;

public interface UserRepository extends MongoRepository<User, String>{

    //spremen v id
    public User findByName(String name);




}

/*public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

}*/