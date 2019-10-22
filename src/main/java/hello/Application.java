package hello;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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
				client.subscribe("DSC010000000001/#", qos);
				client.subscribe("DSC010000000002/#", qos);
				client.subscribe("DSC010000000001/#", qos);
				client.subscribe("PSWS10000000001/#", qos);
				client.subscribe("PSWS20000000001/#", qos);
				client.subscribe("PS3S1P120190323/#", qos);
				client.subscribe("WTHUSB000000002/#", qos);
				client.subscribe("RMgmt/#", qos);
				
			}
			else
				System.out.println("el cliente es NULO");
		//	else
			//	MqttConnect.getInstance();
			System.out.println("Me suscribi");
			
			client = MqttConnect.getInstance().getClient();
			System.out.println("es nukk: "+ client);
			
            
            //agregue proyecto moquette
            
            
        };
    }


}

