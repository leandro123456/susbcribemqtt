package hello;

import org.springframework.web.bind.annotation.RestController;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceConfiguration;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloController {
	private DeviceDAO devdao =  new DeviceDAO();
	DeviceConfiguration devconf = null;
    
	
	//ENVIO DE MENSAJES DE ALARMA
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
    		if(valor.equals("barraparticion"))
    			valor="/";
    		EnviodeMensajes env = new EnviodeMensajes(cliente, valor,devconf.getTopicescribir());
			env.call();
		} catch (Exception e) {
			e.printStackTrace();
			return "fallo_envio";
		}
        return "envio_exitoso";
    }
    
    @RequestMapping(value="enviopulsador/{serial}", method=RequestMethod.POST, produces="application/json")
	public String SendMessagePulsador(@PathVariable String serial,@RequestBody String json) {
    	try {
    		Device device = devdao.retrieveBySerialNumber(serial);
    		if(device.getUsedefaultbrocker())
    			devconf= device.getDeviceconfiguration().get(0);
    		else
    			devconf= device.getDeviceconfiguration().get(1);
    		
    		MqttClient cliente = MqttConnect.getInstance().getClient();
    		System.out.println("*************************ESTA CONECTADA - SendMessagePulsador???????? "+cliente.isConnected());
    		if(!cliente.isConnected()){
    			MqttConnectOptions options = new MqttConnectOptions();
    			options.setAutomaticReconnect(true);
    			options.setUserName("cDashSVR");
    			options.setPassword("av1vEDacfGwXc5".toCharArray());
    			cliente.connect(options);
    		}
    		EnviodeMensajes env = new EnviodeMensajes(cliente, json,devconf.getTopicescribir());
			env.call();
		} catch (Exception e) {
			e.printStackTrace();
			return "fallo_envio";
		}
        return "envio_exitoso";
    }
   
    
    
    
}
