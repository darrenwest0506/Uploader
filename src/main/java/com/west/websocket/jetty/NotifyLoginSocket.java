package com.west.websocket.jetty;
import java.io.IOException;
import java.util.HashSet;

import org.apache.shiro.subject.Subject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.west.data.api.User;
import com.west.data.api.UserRespository;

@Component
@WebSocket
public class NotifyLoginSocket {
	
	static HashSet<Session> activeSessions = new HashSet<Session>();
	
	@Autowired
	private UserRespository userRepository;
	
	private Subject subject;
	
	public void setSubject(Subject subject){
		this.subject = subject;
	}
	
    @OnWebSocketMessage
    public void onText(Session session, String message) throws IOException {
    }
    
    @OnWebSocketConnect
    public void onConnect(Session session) throws IOException {
    	activeSessions.add(session);
    	boolean hasSentMessage = this.subject.getSession().getAttribute("login-message-sent") != null ? (boolean) this.subject.getSession().getAttribute("login-message-sent") : false;
    	if(!hasSentMessage){
    		sendAllActiveUserLoggedOnMessage(session);
    		this.subject.getSession().setAttribute("login-message-sent",true);
    	}
    }

    @OnWebSocketClose
    public void onClose(Session session, int status, String reason) {
    	activeSessions.remove(session);
    }
    
    @SuppressWarnings("unchecked")
	private void sendAllActiveUserLoggedOnMessage(Session loggedInUserSession) throws IOException{
    	User user = userRepository.findByUserName((String) this.subject.getPrincipal());
    	for(Session session: activeSessions){
    		if(loggedInUserSession == session)
    			continue;
    		JSONObject userObj = new JSONObject();
    		userObj.put("name", user.getUserName());
    		session.getRemote().sendString(userObj.toJSONString());
    	}
    }
    
}
