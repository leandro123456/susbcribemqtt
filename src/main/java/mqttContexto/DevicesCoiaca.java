package mqttContexto;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;

public class DevicesCoiaca {
	DeviceDAO devdao =new DeviceDAO();
	
	public void AnalizarMensajeCoiaca(String topico, MqttMessage mensaje){
		System.out.println("Se analizara mensaje");
		String valor = new String(mensaje.getPayload());
		System.out.println("Valor conseguido: "+ valor);
		if (topico.contains("/Status"))
			AnalizarMensajeStatus(topico, mensaje);
		else if (topico.contains("/Partition"))
			AnalizarMensajeParicion(topico,mensaje);
		else if(topico.contains("/Zone")){
			AnalizarMensajeZona(topico,mensaje);
		}else if(topico.contains("/activePartition"))
			AnalizarMensajeParticionActiva(topico,mensaje);
			
		
		/**
		 * todavia faltan los casos
		 * Topico: RMgmt/PSWS10000000001 
		 * message is : {"pwd":"mqttpwd","command":"simulateButtonPush","param1":"PB1"}
		 * 
		 * Topico: PSWS10000000001/state
		 * message is : {"deviceId":"PSWS10000000001","SW1":"ON","PB1LS":1,"PB1TTO":0,"TS":0,"dBm":"-44"}
		 * 
		 * Topico: DSC010000000002/keepAlive
		 * message is : {"deviceID":"DSC010000000002","dateTime":"20191031143910","DSC":1,"MQTT":0,"MQTTRM":1,"dBm":-49}

		 */
		
	}

	private void AnalizarMensajeParticionActiva(String topico, MqttMessage mensaje) {
		String mensaje1 = new String(mensaje.getPayload());
		String serial = topico.replace("/activePartition", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getParticionactiva()!=null && !device.getParticionactiva().equals(mensaje1)){
				device.setParticionactiva(mensaje1);
				devdao.update(device);
				System.out.println("Se recibio mensaje ParticionActiva del serial: "+ serial+"; actualizado exitosamente");
			}
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeZona");
	}

	private void AnalizarMensajeZona(String topico, MqttMessage mensaje) {
		String mensaje1 = new String(mensaje.getPayload());
		String serial = topico.substring(0,topico.indexOf("/Zone"));
		String zona = topico.replace(serial+"/Zone", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getZonasObtenidas()==null){
				device.setZonasObtenidas(new HashMap<String, String>());
				devdao.update(device);
			}
			if(device.getZonasObtenidas().containsKey(zona)){
				if(!device.getZonasObtenidas().get(zona).equals(mensaje1)){
					device.getZonasObtenidas().remove(zona);
					device.getZonasObtenidas().put(zona, mensaje1);
					devdao.update(device);
					System.out.println("Se recibio mensaje Zona del serial: "+ serial+"; actualizado exitosamente");
				}else
					System.out.println("Se recibio mensaje Zona del serial: "+ serial+"; No se actualizo");
			}else{
				device.getZonasObtenidas().put(zona, mensaje1);
				devdao.update(device);
			}
			
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeZona");
	}

	private void AnalizarMensajeParicion(String topico, MqttMessage mensaje) {
		String mensaje1 = new String(mensaje.getPayload());
		String serial = topico.substring(0,topico.indexOf("/Partition"));
		String particion = topico.replace(serial+"/Partition", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getParticiones()==null){
				device.setParticiones(new HashMap<String, String>());
				devdao.update(device);
			}
			if(device.getParticiones().containsKey(particion)){
				if(!device.getParticiones().get(particion).equals(mensaje1)){
					device.getParticiones().remove(particion);
					device.getParticiones().put(particion, mensaje1);
					devdao.update(device);
					System.out.println("Se recibio mensaje Particion del serial: "+ serial+"; actualizado exitosamente");
				}else
					System.out.println("Se recibio mensaje Particion del serial: "+ serial+"; No se actualizo");
			}else{
				device.getParticiones().put(particion, mensaje1);
				devdao.update(device);
			}
			
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeParicion");
	}

	private void AnalizarMensajeStatus(String topico, MqttMessage mensaje) {
		String mensaje1 = new String(mensaje.getPayload());
		String serial = topico.replace("/Status", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			device.setStatus(mensaje1);
			devdao.update(device);
			System.out.println("Se recibio mensaje Status del serial: "+ serial+"; procesado exitosamente");
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeStatus");
		
	}



}
