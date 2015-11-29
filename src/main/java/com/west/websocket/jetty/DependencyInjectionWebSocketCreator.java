package com.west.websocket.jetty;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

public class DependencyInjectionWebSocketCreator implements WebSocketCreator {
 
    private NotifyLoginSocket echo;

 
    public DependencyInjectionWebSocketCreator(NotifyLoginSocket echoWebSocket) {
    	this.echo = echoWebSocket;
    }
 
    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
    	Subject currentUser = SecurityUtils.getSubject();
    	this.echo.setSubject(currentUser);
    	return this.echo;
    }
}