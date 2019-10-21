package hello;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttConnect implements MqttCallback{
	private static MqttConnect mqttconnect= null;
	private static MqttClient client;
	
	private MqttConnect(){
		this.iniciar();
	}
	
	public static MqttConnect getInstance(){
		if(mqttconnect==null){
			mqttconnect=new MqttConnect();
		}
		return mqttconnect;
	}
	
	public void iniciar(){
		String publisherId = UUID.randomUUID().toString();
		try {
			MqttClient publisher = new MqttClient("ws://"+"mqtt.coiaca.com"+":"+"8080","casa",new MemoryPersistence());
			publisher.setCallback(this);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(false);
			options.setConnectionTimeout(5);
			options.setUserName("mqttusr");
			options.setPassword("mqttpwd".toCharArray());
			publisher.connect(options);
		
			if ( !publisher.isConnected()) {
	           	System.out.println("fallo la conexion");
	           	this.client =publisher;
	        }else {
	        	System.out.println("conecto a :" + publisher);
	        	this.client =null;
	        }

			
			
//			esto lo comente
//			CountDownLatch receivedSignal = new CountDownLatch(10);
//			try {
//				System.in.read();
//				System.out.println("obtuvo");
//			} catch (IOException e) {
//				System.out.println("fallo");
//			}
			
			
			
			
//			receivedSignal.await(1, TimeUnit.MINUTES);
			
//			publisher.subscribe("RMgmt/debug");
//			CountDownLatch receivedSignal = new CountDownLatch(10);
//			publisher.subscribe("RMgmt/debug"
//					, (topic, msg) -> {
//			    byte[] payload = msg.getPayload();
//			    // ... payload handling omitted
//			    receivedSignal.countDown();
//			}
//					);    
//			 System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
//			receivedSignal.await(1, TimeUnit.MINUTES);
			
			
//	        MqttMessage msg = makemqttmessage(message);
//	      //  msg.setQos(0);
//	      //  msg.setRetained(true);
//	        publisher.publish(topic,msg); 
				
		} catch (Exception e) {
			System.out.println("mensaje: "+ e.getMessage());
			e.printStackTrace();
		}
		this.client =null;
	}
	
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

   @Override
   public void messageArrived(String topic, MqttMessage message) throws Exception {
       System.out.println("Topico: "+topic);    
   	System.out.println("message is : "+message);
       }

	public MqttClient getClient() {
		return client;
	}
	
	public void setClient(MqttClient client) {
		MqttConnect.client = client;
	}
   
   
}
