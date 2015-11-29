package com.west.shiro;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.west.data.api.User;
import com.west.data.api.UserRespository;

@Component
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRespository userRepository;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(userName == null || password == null){
			resp.setStatus(401);
			return;
		}
		
		UsernamePasswordToken token =
					 new UsernamePasswordToken(userName, password);
		
		//With most of Shiro, you'll always want to make sure you're working with the currently executing user, referred to as the subject
		Subject currentUser = SecurityUtils.getSubject();
		try{
			currentUser.login(token);
			User user = userRepository.findByUserName((String) currentUser.getPrincipal());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
		}catch(AuthenticationException ex){
			resp.setStatus(401);
			return;
		}
		resp.setStatus(200);
	}
}
