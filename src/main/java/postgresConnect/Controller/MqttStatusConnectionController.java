package postgresConnect.Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Pattern;

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

	public static Long horaLong() {
		Calendar c = Calendar.getInstance();
		return c.getTimeInMillis();
	}
	
	public static String hora(){
		String fecha = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
		String[] vector = fecha.split(Pattern.quote("."));
		System.out.println("Proyecto: "+ vector[0]+"Z");
		
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		Date date = new Date(horaLong());
//		long time = date.getTime();
//		Timestamp tim = new Timestamp(time);
//		String result = tim.toString();
//		System.out.println("Esta es la fecha del Test: "+ result);
		return vector[0]+"Z";
	}
}
