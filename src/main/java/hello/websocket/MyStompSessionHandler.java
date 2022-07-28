package hello.websocket;
import org.json.JSONObject;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;

public class MyStompSessionHandler  extends StompSessionHandlerAdapter {

	private Message msg;
	StompSession session;

    public MyStompSessionHandler(Message msg) {
		this.msg=msg;
	}

	@Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    	this.session=session;
        System.out.println("New session established : " + session.getSessionId());
//        session.subscribe("/topic/greetings", this);
//        System.out.println("Subscribed to /topic/greetings");        
        JSONObject json = new JSONObject();
        json.put("name", msg.getText());
        session.send("/app/hello", json.toString().getBytes());
        
        System.out.println("esta conectado!! "+this.getSampleMessage());
        System.out.println("bye");
        session.disconnect();        
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    	System.out.println("Got an exception" +exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        System.out.println("Received : " + msg.getText() +"header: "+headers.getHost() );
        
        session.send("/app/a", getSampleMessage());
        System.out.println("Message sent to websocket server");
        System.out.println("esta conectado!! "+session.isConnected());
        
    }

    /**
     * A sample message instance.
     * @return instance of <code>Message</code>
     */
    public Message getSampleMessage() {
    	System.out.println("$$$$$$$$$$$$$44: "+ msg.getText());
        return msg;
    }
    
    public void setSampleMessage(String mensaje) {
        msg.setText(mensaje);
    }
}
