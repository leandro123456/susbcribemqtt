package postgresConnect.Controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import hello.util.Settings;
import postgresConnect.DAO.MqttStatusConnectionInterface;
import postgresConnect.DAO.MqttStatusConnectionModel;

@RestController
@RequestMapping("/postgresql")
public class MqttStatusConnectionController {
	
	@Autowired
    private MqttStatusConnectionInterface mqttStatusConnectionInterface;
	
	@GetMapping("/testInsert")
	public String getTestInsertStatus() {
		if(Settings.getInstance().getUsarPostgresql()) {
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
		return "no esta configurado";
	}
	
	@GetMapping("/testInsertAll")
	public String getTestInsertAll() {
		if(Settings.getInstance().getUsarPostgresql()) {
		Boolean result1=InserStatusBroker(MqttStatusConnectionModel.ONLINE_BROKER_INT, MqttStatusConnectionModel.ONLINE_BROKER);
		System.out.println("Result 1 status: "+ result1);
		Boolean result2 = InsertAlertaCaida(MqttStatusConnectionModel.DOWN_BROKER, MqttStatusConnectionModel.DOWN_BROKER_INT, "caida de test");
		System.out.println("Result 2 status: "+ result2);
		Boolean result3 =InsertSerialDesconocido(MqttStatusConnectionModel.SERIAL_UNKNOW, MqttStatusConnectionModel.SERIAL_UNKNOW_INT, "dasdsdsd00001");
		System.out.println("Result 3 status: "+ result3);
		return(result1&result2&result3)+"";
		}
		return "no esta configurado";
	}
	
	
	public static Boolean InserStatusBroker(Integer razoncode, String razon) {
		try { 
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/cdashnotifications",
                    "cdash", "123456");
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "Connection OK" : "Connection FAIL");
            String hora=MqttStatusConnectionController.hora();
            System.out.println("HORA: "+ hora);
            PreparedStatement st = connection.prepareStatement(" insert into BrokerConnectionStatus "
            		+ "values('"+"00001"+"','coiaca001','"+razoncode+""
            		+ "','"+razon+"','"+hora+"');");
            st.execute();
            st.close();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            ex.printStackTrace();
            return false;
        }
		
	}
	
	public static Boolean InsertAlertaCaida(String razon, Integer code, String description) {
		if(Settings.getInstance().getUsarPostgresql()) {
		try { 
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/cdashnotifications",
                    "cdash", "123456");
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "Connection OK" : "Connection FAIL");
            String hora=MqttStatusConnectionController.hora();
            PreparedStatement st = connection.prepareStatement(" insert into BrokerFailed "
            		+ "values('coiaca001','"+razon+""
            		+ "','"+code+"','"+description+"','"+hora+"');");
            st.execute();
            st.close();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR al registrar el driver de PostgreSQL: " + ex);
            ex.printStackTrace();
            return false;
        }
		}
		return false;
	}

	public static Boolean InsertSerialDesconocido(String serialUnknow, Integer serialUnknowInt, String serial) {
		try { 
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/cdashnotifications",
                    "cdash", "123456");
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "Connection OK" : "Connection FAIL");
            String hora=MqttStatusConnectionController.hora();
            PreparedStatement st = connection.prepareStatement(" insert into SerialUnknow "
            		+ "values('"+serialUnknow+"','"+serialUnknowInt+"','"+serial+"','"+hora+"');");
            st.execute();
            st.close();
            connection.close();
            return true;
        } catch (Exception ex) {
            System.out.println("ERROR al registrar el driver de PostgreSQL: " + ex);
            ex.printStackTrace();
            return false;
        }
		
	}
	

	
	public static String hora(){
		String fecha = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
		String[] vector = fecha.split(Pattern.quote("."));
		return vector[0]+"Z";
	}


}
