package hello;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;

@RestController
public class AltaDeviceController {
	
	/*
	 * Funcion para dar de alta nuevos dispositivos
	 * en tiempo de ejecucion de la aplicacion.
	 * Cuando se inicia desde cero los busca directamente en base de datos
	 */
	@RequestMapping("nuevodevice/{serial}")
    public String nuevoDevice(@PathVariable String serial) {
    	try {
    		MqttClient client = MqttConnect.getInstance().getClient();
    		int qos = 0;
    		if(client !=null) {
    			System.out.println("esta conectada: "+ client.isConnected());
    			DeviceDAO devdao = new DeviceDAO();
    			Device device= devdao.retrieveBySerialNumber(serial);
    			if(device!=null) {
    				if(device.getUsedefaultbrocker()) {
    					client.subscribe(device.getDeviceconfiguration().get(0).getTopicescuchar(), qos);
    				}
    				else
    					client.subscribe(device.getDeviceconfiguration().get(1).getTopicescuchar(), qos);
    				
    			}
    			else {
    				System.out.println("el device solicitado en la plataforma en Null");
    				return "device no existe";
    			}
    				
    			System.out.println("Me suscribi al topico nuevo en la plataforma");
    		}
    		else {
    			System.out.println("El cliente es NULO");
    			return "El cliente es NULO";
    		}
    		return "cargado exitoso";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
