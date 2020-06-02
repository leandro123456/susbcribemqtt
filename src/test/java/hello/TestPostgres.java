package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import postgresConnect.Controller.MqttStatusConnectionController;
import postgresConnect.DAO.MqttStatusConnectionInterface;
import postgresConnect.DAO.MqttStatusConnectionModel;

public class TestPostgres {
	@Autowired
    private MqttStatusConnectionInterface mqttStatusConnectionInterface;
	
	//@Test
	public void testCreateRegistroPostgres() {
		try {
			MqttStatusConnectionModel status = new MqttStatusConnectionModel();
			status.setBroker("coiaca001");
			status.setBrokerint(1);
			status.setCreated_on(MqttStatusConnectionController.hora());	
			status.setBrokerstatus(MqttStatusConnectionModel.ONLINE_BROKER);
			mqttStatusConnectionInterface.save(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnectionPosgres() {
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
            		+ "values('"+"00001"+"','coiaca001','"+MqttStatusConnectionModel.ONLINE_BROKER_INT+""
            		+ "','"+MqttStatusConnectionModel.ONLINE_BROKER+"','"+hora+"');");
            st.execute();
            st.close();
            connection.close();
            
        } catch (Exception ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            ex.printStackTrace();
        }
		
        
		
	}
	

}
