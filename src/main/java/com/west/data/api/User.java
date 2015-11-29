package com.west.data.api;

import java.util.Date;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

	@Id private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String userName;

	private Date createdDate;
	
	private Date lastLoginDate;
	
	private String password;
	
	private String passwordSalt;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void HashPassword(){
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
	    this.passwordSalt = rng.nextBytes().toString();

		//Now hash the plain-text password with the random salt and multiple
		//iterations and then Base64-encode the value (requires less space than Hex):
		String hashedPasswordBase64 = new Sha256Hash(this.password.toCharArray(), new SimpleByteSource(this.passwordSalt),1024).toBase64();
		this.password = hashedPasswordBase64;
		
	}
	
	@JsonIgnore
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonFormat(pattern="yyyy-MM-dd:hh-mm-ss")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	@JsonIgnore
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	
}




