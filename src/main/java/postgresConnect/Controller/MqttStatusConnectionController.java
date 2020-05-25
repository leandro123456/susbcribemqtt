package postgresConnect.Controller;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import postgresConnect.DAO.MqttStatusConnectionInterface;
import postgresConnect.DAO.MqttStatusConnectionModel;

@RestController
@RequestMapping("/postgresql")
public class MqttStatusConnectionController {
	
	@Autowired
    private MqttStatusConnectionInterface mqttStatusConnectionInterface;
	
	@GetMapping("/testInsert")
	public String getTestInsertStatus() {
		try {
			MqttStatusConnectionModel status = new MqttStatusConnectionModel();
			status.setBroker("coiaca001");
			status.setBrokerint(1);
			status.setCreated_on(hora());	
			status.setBrokerstatus(MqttStatusConnectionModel.ONLINE_BROKER);
			mqttStatusConnectionInterface.save(status);
			return "exitoso";
		} catch (Exception e) {
			e.printStackTrace();
			return "fallo";
		}
		
		
	}

	
	
	public static String hora(){
		String fecha = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
		System.out.println("Esta es la fecha del Test: "+ fecha);
		return fecha;
	}
}
