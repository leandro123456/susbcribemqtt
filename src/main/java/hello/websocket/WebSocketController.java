package hello.websocket;

import java.net.URI;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class WebSocketController {
	
	@PostMapping("/ta")
	public void create(@RequestBody Map<String, String> json) {
		try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(
            		new URI(" ws://localhost:8080/gs-guide-websocket/app/hello"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{'name':'addChannel'}");

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (Exception ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
            ex.printStackTrace();
        }
	}

}
