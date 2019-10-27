package hello;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import Persistence.Model.Device;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("INICIO");
            
//            inicio  del mio
        	MqttClient client = MqttConnect.getInstance().getClient();
        	System.out.println("Esta conectada loro");
			int qos = 0;
			//if(MqttConnect.getInstance()!= null)
			if(client !=null) {
				List<String> topicos= Device.obtenerClientesCoiaca();
				for(String topico : topicos) {
					client.subscribe(topico, qos);
				}
				client.subscribe("RMgmt/#", qos);
				
			}
			else
				System.out.println("el cliente es NULO");
		//	else
			//	MqttConnect.getInstance();
			System.out.println("Me suscribi");
			
			client = MqttConnect.getInstance().getClient();
			System.out.println("es nukk: "+ client);
			System.out.println("esta conectada: "+ client.isConnected());
			
            
            //agregue proyecto moquette
            
            
        };
    }


}

