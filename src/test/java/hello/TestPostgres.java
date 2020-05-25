package hello;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import postgresConnect.Controller.MqttStatusConnectionController;
import postgresConnect.DAO.MqttStatusConnectionInterface;
import postgresConnect.DAO.MqttStatusConnectionModel;

public class TestPostgres {
	@Autowired
    private MqttStatusConnectionInterface mqttStatusConnectionInterface;
	
	@Test
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

}
