package br.com.lucas.brewer.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws/teste")
public class HelloWebSocket {
	
	private static Collection<Session> SESSION;

	static {
		SESSION = Collections.synchronizedCollection(new HashSet<Session>());
	}
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println(">>>>> WEBSOCKET");
		SESSION.add(session);
	}
	
	@OnMessage
	public void onMessage(String message) throws IOException {
		System.out.println(String.format("RECEBENDO MENSAGEM: %s", message) );
		for (Session session2 : SESSION) {
			session2.getBasicRemote().sendText(message);
		}
	}
	
}
