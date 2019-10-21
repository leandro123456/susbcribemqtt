package hello;

import org.springframework.web.bind.annotation.RestController;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceConfiguration;

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
    		EnviodeMensajes env = new EnviodeMensajes(cliente, valor,devconf.getTopicescribir());
			env.call();
    		
			
			//esto se comento
//    		IMqttClient publisher = new MqttClient("ws://"+devconf.getIphostescuchar()+":"+devconf.getPortescuchar(),"casa",new MemoryPersistence());
//			MqttConnectOptions options = new MqttConnectOptions();
//			options.setAutomaticReconnect(true);
//			//options.setCleanSession(false);
//			options.setConnectionTimeout(5);
//			options.setUserName(devconf.getUserescuchar());
//			options.setPassword(devconf.getPassescuchar().toCharArray());
//        	System.out.println("antes de conectar");
//			publisher.connect(options);
//			System.out.println("despues de conectar");
//			if (publisher.isConnected()) {
//	           	System.out.println("ESTABA conectada");
//	    		EnviodeMensajes env = new EnviodeMensajes(publisher, valor,devconf.getTopicescribir());
//				env.call();
//	        }else {
//	        	System.out.println("NO ESTABA CONECTADA!!");
//
//	        	EnviodeMensajes env = new EnviodeMensajes(publisher, valor,devconf.getTopicescribir());
//				env.call();
//	        }
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "Se envio con exito";
    }
    
}
