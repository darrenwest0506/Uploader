package com.west.websocket.jetty;

import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class DependencyInjectionSocketServlet  extends WebSocketServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * Time in ms for how long a web socket will stay idle open.
	 */
	private static final long webSocketIdleTime = 10000;
	
	private WebSocketCreator creator;
	
	public DependencyInjectionSocketServlet(DependencyInjectionWebSocketCreator socket) {
		this.creator = socket;
	}
	
    @Override
    public void configure(WebSocketServletFactory factory) {
    	factory.getPolicy().setIdleTimeout(DependencyInjectionSocketServlet.webSocketIdleTime);
        factory.setCreator(creator);
    }
}

