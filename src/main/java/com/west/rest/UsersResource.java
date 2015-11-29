package com.west.rest;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.west.data.api.User;
import com.west.data.api.UserRespository;
import com.west.util.IdHolder;



@RestController
@RequestMapping(UsersResource.PATH)

public class UsersResource {

	static final String PATH = "api/users";
	
	@Autowired
	private UserRespository repository;
	
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<User> getUsers(){
		return repository.findAll();
	}	
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getUser(@PathVariable("id") String id) {
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<IdHolder> createUser(@RequestBody User user)
            throws Exception {
    	user.setCreatedDate(new Date());
    	
    	user.HashPassword();
    	
        repository.insert(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(PATH + "/" + user.getId()));
        return new ResponseEntity<IdHolder>(new IdHolder(user.getId()), headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void updateUser(@RequestBody User user) {
        repository.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteUser(@PathVariable("id") String id) {
        repository.delete(id);
    }
}


