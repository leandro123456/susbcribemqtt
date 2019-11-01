package mqttContexto;

import java.util.Date;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceNotification;

public class DevicesCoiaca {
	DeviceDAO devdao =new DeviceDAO();
	
	public void AnalizarMensajeCoiaca(String topico, MqttMessage mensaje){
		String valor = new String(mensaje.getPayload());
		System.out.println("Valor conseguido: "+ valor);
		if (topico.contains("/Status"))
			AnalizarMensajeStatus(topico, valor);
		else if (topico.contains("/Partition"))
			AnalizarMensajeParicion(topico,valor);
		else if(topico.contains("/Zone"))
			AnalizarMensajeZona(topico,valor);
		else if(topico.contains("/activePartition"))
			AnalizarMensajeParticionActiva(topico,valor);
		else if(topico.contains("/state") )
			AnalizarMensajeEstadoDevices(topico,valor);
		else if(topico.contains("RMgmt/") || topico.contains("/keepAlive"))
			AnalizarMensajeRMgmtDevices(topico,valor);
	}

	private void AnalizarMensajeRMgmtDevices(String topico, String mensaje) {
		String serial = "";
		if(topico.contains("/state"))
			serial = topico.replace("/state", "");
		if(topico.contains("RMgmt/"))
			serial = topico.replace("RMgmt/", "");
		if(topico.contains("/keepAlive"))
			serial = topico.replace("/keepAlive", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getLastnotification()==null)
				device.setLastnotification(new DeviceNotification());
			device.getLastnotification().setName(device.getTipo());
			device.getLastnotification().setContent(mensaje);
			device.getLastnotification().setTime(new Date().toString());
			devdao.update(device);
			System.out.println("Se recibio mensaje Estado del serial: "+ serial+"; actualizado exitosamente");
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeEstadoDevices");
	}

	private void AnalizarMensajeEstadoDevices(String topico, String mensaje) {
		String serial = topico.replace("/state", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getLastnotification()==null)
				device.setLastnotification(new DeviceNotification());
			device.getLastnotification().setName(device.getTipo());
			device.getLastnotification().setContent(mensaje);
			device.getLastnotification().setTime(new Date().toString());
			devdao.update(device);
			System.out.println("Se recibio mensaje Estado del serial: "+ serial+"; actualizado exitosamente");
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeEstadoDevices");
		
	}

	private void AnalizarMensajeParticionActiva(String topico, String mensaje) {
		String serial = topico.replace("/activePartition", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getParticionactiva()!=null && !device.getParticionactiva().equals(mensaje)){
				device.setParticionactiva(mensaje);
				devdao.update(device);
				System.out.println("Se recibio mensaje ParticionActiva del serial: "+ serial+"; actualizado exitosamente");
			}
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeZona");
	}

	private void AnalizarMensajeZona(String topico, String mensaje) {
		String serial = topico.substring(0,topico.indexOf("/Zone"));
		String zona = topico.replace(serial+"/Zone", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getZonasObtenidas()==null){
				device.setZonasObtenidas(new HashMap<String, String>());
				devdao.update(device);
			}
			if(device.getZonasObtenidas().containsKey(zona)){
				if(!device.getZonasObtenidas().get(zona).equals(mensaje)){
					device.getZonasObtenidas().remove(zona);
					device.getZonasObtenidas().put(zona, mensaje);
					devdao.update(device);
					System.out.println("Se recibio mensaje Zona del serial: "+ serial+"; actualizado exitosamente");
				}else
					System.out.println("Se recibio mensaje Zona del serial: "+ serial+"; No se actualizo");
			}else{
				device.getZonasObtenidas().put(zona, mensaje);
				devdao.update(device);
			}
			
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeZona");
	}

	private void AnalizarMensajeParicion(String topico, String mensaje) {
		String serial = topico.substring(0,topico.indexOf("/Partition"));
		String particion = topico.replace(serial+"/Partition", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			if(device.getParticiones()==null){
				device.setParticiones(new HashMap<String, String>());
				devdao.update(device);
			}
			if(device.getParticiones().containsKey(particion)){
				if(!device.getParticiones().get(particion).equals(mensaje)){
					device.getParticiones().remove(particion);
					device.getParticiones().put(particion, mensaje);
					devdao.update(device);
					System.out.println("Se recibio mensaje Particion del serial: "+ serial+"; actualizado exitosamente");
				}else
					System.out.println("Se recibio mensaje Particion del serial: "+ serial+"; No se actualizo");
			}else{
				device.getParticiones().put(particion, mensaje);
				devdao.update(device);
			}
			
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeParicion");
	}

	private void AnalizarMensajeStatus(String topico, String mensaje) {
		String serial = topico.replace("/Status", "");
		Device device = devdao.retrieveBySerialNumber(serial);
		if(device!=null){
			device.setStatus(mensaje);
			devdao.update(device);
			System.out.println("Se recibio mensaje Status del serial: "+ serial+"; procesado exitosamente");
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeStatus");
		
	}



}
