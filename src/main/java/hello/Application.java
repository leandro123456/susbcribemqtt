package hello;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import Persistence.Model.Device;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> setPort() {
		return factory -> {
			factory.setPort(8090);
		};
    }
    

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            inicio  del mio
        	MqttClient client = MqttConnect.getInstance().getClient();
			int qos = 0;
			if(client !=null) {
				System.out.println("esta conectada: "+ client.isConnected());
//				List<String> topicos= Device.obtenerClientesCoiaca();
//				for(String topico : topicos) {
//					client.subscribe(topico, qos);
//				}
				client.subscribe("RMgmt/#", qos);
				System.out.println("Me suscribi a todos los topicos en la plataforma");
				
			}
			else
				System.out.println("el cliente es NULO");		
            
            //agregue proyecto moquette para un segundo broker y suscribirme a los dos;           
        };
    }
}

