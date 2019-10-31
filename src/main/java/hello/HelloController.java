package hello;

import org.springframework.web.bind.annotation.RestController;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceConfiguration;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	private DeviceDAO devdao =  new DeviceDAO();
	DeviceConfiguration devconf = null;
    
    @RequestMapping("envio/{serial}/{valor}")
    public String index(@PathVariable String valor, @PathVariable String serial) {
    	try {
    		System.out.println("mensaje resibido: "+ valor);
    		Device device = devdao.retrieveBySerialNumber(serial);
    		if(device.getUsedefaultbrocker())
    			devconf= device.getDeviceconfiguration().get(0);
    		else
    			devconf= device.getDeviceconfiguration().get(1);
    		
    		MqttClient cliente = MqttConnect.getInstance().getClient();
    		System.out.println("*************************ESTA CONECTADA???????? "+cliente.isConnected());
    		if(!cliente.isConnected()){
    			MqttConnectOptions options = new MqttConnectOptions();
    			options.setAutomaticReconnect(true);
    			options.setUserName("cDashSVR");
    			options.setPassword("av1vEDacfGwXc5".toCharArray());
    			cliente.connect(options);
    		}
    		EnviodeMensajes env = new EnviodeMensajes(cliente, valor,devconf.getTopicescribir());
			env.call();
    		
			
//			esto se comento
//    		String publisherId = UUID.randomUUID().toString();
//    		IMqttClient publisher = new MqttClient("tcp://"+devconf.getIphostescuchar()+":"+"1883",publisherId);
//			MqttConnectOptions options = new MqttConnectOptions();
//			options.setAutomaticReconnect(false);
//			//options.setCleanSession(false);
//			//options.setConnectionTimeout(5);
//			options.setUserName(devconf.getUserescuchar());
//			options.setPassword(devconf.getPassescuchar().toCharArray());
//        	System.out.println("antes de conectar");
//			publisher.connect(options);
//			System.out.println("despues de conectar");
//			if (publisher.isConnected()) {
//	    		EnviodeMensajes env = new EnviodeMensajes(publisher, valor,devconf.getTopicescribir());
//				env.call();
//	        }else {
//	        	EnviodeMensajes env = new EnviodeMensajes(publisher, valor,devconf.getTopicescribir());
//	        	env.call();
//	        }
		} catch (Exception e) {
			e.printStackTrace();
			return "fallo_envio";
		}
        return "envio_exitoso";
    }
    
}
