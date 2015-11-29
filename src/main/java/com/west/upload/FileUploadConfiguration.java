package com.west.upload;

import javax.servlet.http.HttpServlet;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfiguration {
	  
	@Bean
	public HttpServlet uploadServlet(){
	  return new FileUploadServlet();
	}
	
	@Bean
	public ServletRegistrationBean uploadServletRegistration(){
		return new ServletRegistrationBean(uploadServlet(),"/Upload");
	}
	  
}
