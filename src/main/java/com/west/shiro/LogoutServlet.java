package com.west.shiro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class LogoutServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

//	@Autowired
//	private UserRespository userRepository;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//With most of Shiro, you'll always want to make sure you're working with the currently executing user, referred to as the subject
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		resp.sendRedirect(resp.encodeRedirectURL("/Login.html"));
	}
}
