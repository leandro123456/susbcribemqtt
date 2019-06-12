package hello;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application implements MqttCallback{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("INICIO");
            
        		String publisherId = UUID.randomUUID().toString();
        		try {
        			MqttClient publisher = new MqttClient("tcp://"+"mqtt.coiaca.com"+":"+"1883",publisherId);
        			publisher.setCallback(this);
        			MqttConnectOptions options = new MqttConnectOptions();
        			options.setAutomaticReconnect(true);
        			options.setCleanSession(true);
        			options.setConnectionTimeout(10);
        			options.setUserName("mqttusr");
        			options.setPassword("mqttpwd".toCharArray());
        			publisher.connect(options);
        		
        			if ( !publisher.isConnected()) {
        	           	System.out.println("fallo la conexion");
        	        }else {
        	        	System.out.println("conecto a :" + publisher);
        	        }
        			int qos = 1;
        			publisher.subscribe("RMgmt/debug", qos);
        			
        			CountDownLatch receivedSignal = new CountDownLatch(10);
        			try {
        				System.in.read();
        				System.out.println("obtuvo");
        			} catch (IOException e) {
        				System.out.println("fallo");
        			}
        			receivedSignal.await(1, TimeUnit.MINUTES);
        			
        			//publisher.subscribe("RMgmt/debug");
//        			CountDownLatch receivedSignal = new CountDownLatch(10);
//        			publisher.subscribe("RMgmt/debug"
//        					, (topic, msg) -> {
//        			    byte[] payload = msg.getPayload();
//        			    // ... payload handling omitted
//        			    receivedSignal.countDown();
//        			}
//        					);    
//        			 System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
//        			receivedSignal.await(1, TimeUnit.MINUTES);
        			
        			
//        	        MqttMessage msg = makemqttmessage(message);
//        	      //  msg.setQos(0);
//        	      //  msg.setRetained(true);
//        	        publisher.publish(topic,msg); 
        				
        		} catch (Exception e) {
        			System.out.println("mensaje: "+ e.getMessage());
        			e.printStackTrace();
        		}
        };
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
            System.out.println("message is : "+message);
        }

}

