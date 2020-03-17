package mqttContexto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import Persistence.DAO.DeviceDAO;
import Persistence.DAO.UserDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceNotification;
import Persistence.Model.Notificacion;
import Persistence.Model.User;
import hello.FirebaseController;

public class DevicesCoiaca {
	DeviceDAO devdao =new DeviceDAO();
	UserDAO userdao = new UserDAO();
	
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
			if(device.getParticionactiva()!=null ){
				if(!device.getParticionactiva().equals(mensaje)){
					device.setParticionactiva(mensaje);
					devdao.update(device);
					System.out.println("Se recibio mensaje ParticionActiva del serial: "+ serial+"; actualizado exitosamente");
				}
			}
			else{
				device.setParticionactiva(new String());
				devdao.update(device);
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
			System.out.println("----------------------------------------------------");
			System.out.println("----------------------------------------------------");
			System.out.println("----------------------------------------------------");
			System.out.println("MENSAJE RECIBIDO			"+ mensaje);
			System.out.println("----------------------------------------------------");
			System.out.println("----------------------------------------------------");
			System.out.println("----------------------------------------------------");
			if(!mensaje.equals("pending"))
				EnviarNotificacionFirebase(device,mensaje);
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeParicion");
	}

	private void EnviarNotificacionFirebase(Device device, String mensaje) {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add(new String(Base64.getDecoder().decode(device.getUserowner().getBytes())));
		destinatarios.addAll(device.getUsers());
		destinatarios.addAll(device.getAdmins());

		FirebaseController fire = new FirebaseController();
		for(String user: destinatarios) {
			System.out.println("------------------------ENVIO DE NOTIFICACION: "+ user);
			if(enviarNotificacion(user,mensaje))
				fire.enviarNotificacion(user, "Su alarma a cambiado a estado: "+ mensaje);
		}
		
	}

	private boolean enviarNotificacion(String username, String mensaje) {
		try {
			User user =userdao.retrieveByMail(username);
			if(user!=null) {
				if((mensaje.contains("armed") || mensaje.contains(Notificacion.DISARMED))&& user.getNotificaciones().get(Notificacion.CONDICION_ARMADO)!=null && user.getNotificaciones().get(Notificacion.CONDICION_ARMADO))
					return true;
				else if(mensaje.contains(Notificacion.TRIGERED) && user.getNotificaciones().get(Notificacion.CONDICION_DISPARADO)!=null && user.getNotificaciones().get(Notificacion.CONDICION_DISPARADO))
					return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
