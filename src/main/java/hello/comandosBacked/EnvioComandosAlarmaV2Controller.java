package hello.comandosBacked;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Persistence.DAO.DeviceDAO;
import Persistence.DAO.UserDAO;
import Persistence.Model.Device;
import Persistence.Model.User;
import hello.util.Settings;

@RestController
public class EnvioComandosAlarmaV2Controller {
	
	
	@RequestMapping("ejecutaraccion/{username}/{valor}")
	public void envioDeComandoParaArmarDesarmarAlarma(@PathVariable String username,@PathVariable String valor) {
		String serverUri= Settings.getInstance().getUriBroker();
		String userName= Settings.getInstance().getUserNameBroker();
		String password= Settings.getInstance().getPasswordBroker();
		String publisherId = UUID.randomUUID().toString();
		try {
			String[] a= valor.split("-");
			String serial= a[0];
			String accion= a[1];
			String particion=a[2];
			IMqttClient publisher = new MqttClient(serverUri,publisherId);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			options.setUserName(userName);
			options.setPassword(password.toCharArray());
			publisher.connect(options);		
			if ( !publisher.isConnected()) {
				System.out.println("fallo la conexion al enviar la orden a la alarma!!!");
			}else {
				UserDAO userdao=new UserDAO();
				username=new String(Base64.getDecoder().decode(username));
				User user= userdao.retrieveByMail(username);
				String codDesarmado= user.getDeviceserialnumber().get(serial);
				String topico=serial+"/cmd";
				if(accion.equals("desarmar")) {
					try {
						char[]val=codDesarmado.toCharArray();
						for(char value: val) {
							System.out.println(value);
							MqttMessage msg = makemqttmessageString(value+"");
							msg.setQos(0);
							msg.setRetained(true);
							publisher.publish(topico,msg); 
							TimeUnit.SECONDS.sleep(3);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
			    	String result="";
			    	if(accion.contains("armarzona")) result=particion+"S";
			    	else result=particion+"A"; 
					MqttMessage msg = makemqttmessageString(result);
					msg.setQos(0);
					msg.setRetained(true);
					publisher.publish(topico,msg); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@RequestMapping("cambioparticion/{serial}/{numparticion}")
	public void cambioDeParticionAlarma(@PathVariable String serial,@PathVariable String numparticion) {
		String serverUri= Settings.getInstance().getUriBroker();
		String userName= Settings.getInstance().getUserNameBroker();
		String password= Settings.getInstance().getPasswordBroker();
		String publisherId = UUID.randomUUID().toString();
		try {
			IMqttClient publisher = new MqttClient(serverUri,publisherId);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			options.setUserName(userName);
			options.setPassword(password.toCharArray());
			publisher.connect(options);		
			if ( !publisher.isConnected()) {
				System.out.println("fallo la conexion al enviar la orden a la alarma!!!");
			}else {
				String topico=serial+"/activePartition";
				MqttMessage msg = makemqttmessageString(numparticion);
				msg.setQos(0);
				msg.setRetained(true);
				publisher.publish(topico,msg); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MqttMessage makemqttmessageString(String message1) {                       
	  	 MqttMessage message = new MqttMessage();
	  	 message.setPayload(message1.getBytes());
	  	 return message;
	  }	
	
	@GetMapping("updatepass/{useremail}/{serial}/{pass}")
	public String updatePassAlarma(@PathVariable String useremail,@PathVariable String serial,@PathVariable String pass) {
		try {
			System.out.println("user!!!!: "+ useremail);
			UserDAO userdao= new UserDAO();
			useremail=new String(Base64.getDecoder().decode(useremail));
			User user= userdao.retrieveByMail(useremail);
			if(user.getDeviceserialnumber().containsKey(serial)) {
				user.getDeviceserialnumber().replace(serial,pass);
			}
			userdao.update(user);
			return "successful - password updated";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "failed - internal error";
		}
		
	}
}
