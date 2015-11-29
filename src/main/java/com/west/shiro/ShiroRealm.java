package com.west.shiro;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.west.data.api.User;
import com.west.data.api.UserRespository;

@Component
public class ShiroRealm extends AuthenticatingRealm{

	@Autowired
	private UserRespository userRepository;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {		
		UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
		String username = userPassToken.getUsername();

		if (username == null) {
			return null;
		}

		User user = userRepository.findByUserName(username);	
		
		if(user == null){
			return null;
		}	
		// return salted credentials
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "ShiroRealm");
		info.setCredentialsSalt(new SimpleByteSource(user.getPasswordSalt()));
		return info;
	}
	

	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
		matcher.setHashIterations(1024);
		matcher.setStoredCredentialsHexEncoded(false);
		return matcher;
	}
	
	

}
