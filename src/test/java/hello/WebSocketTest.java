package hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

import hello.websocket.Message;
import hello.websocket.MyStompSessionHandler;
import hello.websocket.WebsocketClientEndpoint;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class WebSocketTest {

	@Test
	public void testSendWSStomp() {
		try {
			WebSocketClient client = new StandardWebSocketClient();
			WebSocketStompClient stompClient = new WebSocketStompClient(client);
			SimpleMessageConverter messageConverter = new SimpleMessageConverter();
		    messageConverter.toMessage("casadddddd", null);
			stompClient.setMessageConverter(messageConverter);

			Message msg= new Message();
			msg.setText("NAMEEEEEEE12");
			StompSessionHandler sessionHandler = new MyStompSessionHandler(msg);
			stompClient.connect("ws://localhost:8080/gs-guide-websocket", sessionHandler);
			
			long begin = System.currentTimeMillis();
			boolean timeOver = false;
			 System.out.println(System.currentTimeMillis());
			while (!timeOver){
			  long currTime = System.currentTimeMillis() - begin;
			  if (currTime >= 1000){
				  System.out.println("salo");
			     timeOver = true;
			     System.exit(0);
			 }
			
//			String s = "Hello World! 3 + 3= 6";
//
//		      // create a new scanner with the specified String Object
//		      Scanner scanner = new Scanner(s);
//
//		      // print the next line of the string
//		      System.out.println("" + scanner.nextLine());
//
//		      // close the scanner
//		      System.out.println("Closing Scanner...");
//		      scanner.close();
//		      System.out.println("Scanner Closed.");
			}
		      
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
