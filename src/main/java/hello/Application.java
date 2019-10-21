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
        	//MqttClient client = MqttConnect.getInstance().getClient();
        	System.out.println("Esta conectada loro");
			int qos = 0;
			MqttConnect.getInstance().getClient().subscribe("DSC010000000002/#", qos);
			System.out.println("Me suscribi");
        };
    }


}

