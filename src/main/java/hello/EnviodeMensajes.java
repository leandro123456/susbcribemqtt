package hello;

import java.util.UUID;
import java.util.concurrent.Callable;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class EnviodeMensajes implements Callable<Void>{

    private IMqttClient client;
    private String valor = "";
    private String topic = "";

    public EnviodeMensajes(IMqttClient client, String valor, String topico) {
        this.client = client;
        this.valor = valor;
        this.topic= topico;
    }

    @Override
    public Void call() throws Exception {
        if ( !client.isConnected()) {
            System.out.println("Client no connectado.");
            client.connect();
            return null;
        }
        String json= valor;
        MqttMessage msg = makemqttmessageString(json);
        msg.setQos(0);
        client.publish(topic,msg);        
        return null;        
    }
    
    
    
    
	@SuppressWarnings("resource")
	public static void sendtimerString(String timerstringvalue,String serverUri, String port, String topic,
			String userName, String password) {
		String publisherId = UUID.randomUUID().toString();
		try {
			IMqttClient publisher = new MqttClient(serverUri+":"+port,publisherId);
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			options.setUserName(userName);
			options.setPassword(password.toCharArray());
			publisher.connect(options);		
			if ( !publisher.isConnected()) {
	           	System.out.println("fallo la conexion");
	        } 
		   	 String json= ArmarMensajeAlarma("alarm-armarzona","1");
		   	 System.out.println("el json: "+json);
	        MqttMessage msg = makemqttmessageString(json);
	        msg.setQos(0);
	        msg.setRetained(true);
	        publisher.publish(topic,msg); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
    private static String ArmarMensajeAlarma(String mensaje, String particion) {
    	String result="";
    	if(mensaje.contains("armarzona"))
    		result=particion+"S";
    	else if(mensaje.contains("armartotal"))
    			result=particion+"A"; 
    	else
    		result=mensaje.replace("alarm-", "");
		return result;
	}


	public static MqttMessage makemqttmessageString(String message1) {                       
	  	 MqttMessage message = new MqttMessage();
	  	 message.setPayload(message1.getBytes());
	  	 return message;
	  	 
	  }	
	
	
}