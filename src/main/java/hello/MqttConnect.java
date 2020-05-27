package hello;

import java.util.Date;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import mqttContexto.DevicesCoiaca;

public class MqttConnect implements MqttCallback{
	private static MqttConnect mqttconnect= null;
	private MqttClient client;
	private DevicesCoiaca devcoiaca= new DevicesCoiaca();
	
	private MqttConnect(){
		this.iniciar();
	}
	
	public static MqttConnect getInstance(){
		if(mqttconnect==null){
			System.out.println("******************* mqttconnect es null");
			mqttconnect=new MqttConnect();
		}
		return mqttconnect;
	}
	
	public void iniciar(){
		String publisherId = UUID.randomUUID().toString();
		System.out.println("ejecutar el inicio");
		try {
			MqttClient publisher = new MqttClient("tcp://"+"localhost"+":"+"1883",publisherId,new MemoryPersistence());
			publisher.setCallback(this);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			//options.setCleanSession(false);
			//options.setConnectionTimeout(35);
			options.setUserName("cDashSVR");
			options.setPassword("av1vEDacfGwXc5".toCharArray());
			if ( !publisher.isConnected()) {
	           	System.out.println("************************no esta conectado");
	           	publisher.connect(options);
	           	this.client =publisher;
	        }else {
	        	System.out.println("************************conecto a :" + publisher);
	        	this.client =publisher;
	        }
				
		} catch (Exception e) {
			System.out.println("*******************mensaje: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("ERORR ++++++++++++++++++++++++++++");
		Date fecha = new Date();
		System.out.println("ERROR  SE PERDIO LA CONECCION: "+ fecha.toString());
		iniciar();
		
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

   @Override
   public void messageArrived(String topic, MqttMessage message) throws Exception {
//	   System.out.println("Topico: "+topic);    
//	   System.out.println("message is : "+message);
	   devcoiaca.AnalizarMensajeCoiaca(topic, message);
       }

	public MqttClient getClient() {
		return client;
	}
	
	public void setClient(MqttClient client) {
		this.client = client;
	}
   
   
}
