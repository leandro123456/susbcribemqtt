package mqttContexto;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ServicioStatusBroker {


	public static void validarstatus(String topic, MqttMessage message) {
		System.out.println("Mensaje Obtenido: "+ message);
		
	}

}
