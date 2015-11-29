package com.west.websocket.jetty;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfiguration {
	  @Bean
	  public NotifyLoginSocket echoService(){
		  return new NotifyLoginSocket();
	  }
	  @Bean
	  public ServletRegistrationBean echoServlet(){
		  return new ServletRegistrationBean(new DependencyInjectionSocketServlet(socketCreator()), "/LoginSocket");
	  }
	  
	  @Bean
	  public DependencyInjectionWebSocketCreator socketCreator(){
		return new DependencyInjectionWebSocketCreator(echoService()); 
	  }
}
