package com.west.data.api;


import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRespository extends MongoRepository<User, String>  {
	
    public User findByUserName(String firstName);
	
}

