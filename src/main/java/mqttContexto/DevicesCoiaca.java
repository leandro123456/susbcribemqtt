package mqttContexto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import Persistence.DAO.DeviceDAO;
import Persistence.DAO.UserDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceNotification;
import Persistence.Model.Notificacion;
import Persistence.Model.User;
import hello.FirebaseController;
import hello.MailController;
import postgresConnect.Controller.MqttStatusConnectionController;
import postgresConnect.DAO.MqttStatusConnectionModel;

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
		}else {
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeEstadoDevices");
			MqttStatusConnectionController.InsertSerialDesconocido(MqttStatusConnectionModel.SERIAL_UNKNOW, 
					MqttStatusConnectionModel.SERIAL_UNKNOW_INT, serial);
		}
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
			//cargarZona
			gestionarHoraEnZona(zona,mensaje,device);
			
			//cargar Zona luego de Trigger o Trouble
			cargarZonasEnAlarmaDisparada(device.getAlarmaTriggerTrouble(),zona,mensaje,device);
			
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
				}else {
					System.out.println("Se recibio mensaje Particion del serial: "+ serial+"; No se actualizo");
					//insertar en base de datos
					MqttStatusConnectionController.InsertAlertaCaida(MqttStatusConnectionModel.DOWN_BROKER, 
							MqttStatusConnectionModel.DOWN_BROKER_INT, "mqttclient caida sin envio de notificacion");
					return;
				}
			}else{
				device.getParticiones().put(particion, mensaje);
				devdao.update(device);
			}
			System.out.println("Notificacion del Mensaje: "+ mensaje);
			if(!mensaje.equals("pending")) {
				EnviarNotificacion(device,mensaje);
			}else
				System.out.println("Este es otro mensaje: "+ mensaje);
		}else
			System.out.println("ERROR: Serial: " + serial +"; en la plataforma es NULL. AnalizarMensajeParicion");
	}

	private void EnviarNotificacion(Device device, String mensaje) {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add(new String(Base64.getDecoder().decode(device.getUserowner().getBytes())));
		destinatarios.addAll(device.getUsers());
		destinatarios.addAll(device.getAdmins());
		FirebaseController fire = new FirebaseController();
		MailController mail = new MailController();
		for(String user: destinatarios) {
			//Envio de PUSH Firebase
			boolean enviarNotificacion= enviarNotificacion(user,mensaje);
			if(enviarNotificacion && mensaje.contains(Notificacion.TRIGERED)) {
				fire.enviarNotificacion(user, "¡Su alarma se ha Disparado! \n Verifique el Estado");
			}else if (enviarNotificacion) {
				fire.enviarNotificacion(user, "Su alarma a cambiado a estado: "+ mensaje);
			}
			//Envio de MAIL
			boolean enviarMail = enviarMail(user,mensaje);
			if(enviarMail && mensaje.contains(Notificacion.TRIGERED)) {
				mail.enviarNotificacion(user, "¡Su alarma se ha Disparado! \n Verifique el Estado");
			}else if (enviarMail) {
				mail.enviarNotificacion(user, "Su alarma a cambiado a estado: "+ mensaje);
			}
		}
		
	}

	private boolean enviarMail(String username, String mensaje) {
		try {
			User user =userdao.retrieveByMail(username);
			if(user!=null) {
				if((mensaje.contains("armed") || mensaje.contains(Notificacion.DISARMED))&&  
						user.getNotificaciones().get(Notificacion.CONDICION_ARMADO) && 
						user.getNotificaciones().get(Notificacion.ENVIAR_MAIL))
					return true;
				else if(mensaje.contains(Notificacion.TRIGERED) && 
						user.getNotificaciones().get(Notificacion.CONDICION_DISPARADO) && 
						user.getNotificaciones().get(Notificacion.ENVIAR_MAIL))
					return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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


	private void gestionarHoraEnZona(String zona,String status, Device device) {
		System.out.println("gestionarHoraEnZona");
		if(device.getUltimaszonas()!=null) {
			if(device.getUltimaszonas().size()>=20) 
				quitarRegistroViejo(device);
			device.getUltimaszonas().add(zona+";"+status+";"+hora());
			devdao.update(device);
		}else {
			System.out.println("gestionarHoraEnZona Lista vacia");
			device.setUltimaszonas(new ArrayList<>());
			devdao.update(device);
		}
	}

	public void quitarRegistroViejo(Device device) {
		System.out.println("Quitar Registro Mas viejo");
		int posicionmasviejo=0;
		Date fechamasviejo=null;
		Collections.sort(device.getUltimaszonas());  
		SimpleDateFormat formatter=new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		try {
		for(int i=0; i<device.getUltimaszonas().size();i++) {
			String[] vector=device.getUltimaszonas().get(i).split(Pattern.quote(";"));
			if(i==0) {
				 fechamasviejo= formatter.parse(vector[2]);
			}else if(fechamasviejo.after(formatter.parse(vector[2])))
				posicionmasviejo=i;
		}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		device.getUltimaszonas().remove(posicionmasviejo);
		devdao.update(device);
	}
	

	private void cargarZonasEnAlarmaDisparada(String alarmaTriggerTrouble, String zona, String mensaje, Device device) {
		if(alarmaTriggerTrouble!=null && !alarmaTriggerTrouble.equals("")) {
			String[] vector = alarmaTriggerTrouble.split(Pattern.quote("."));
			Boolean status=Boolean.parseBoolean(vector[0]);
			if(status) {
				if(device.getZonasluegodisparo()!=null) {
					if(device.getZonasluegodisparo().size()>=20) {
						MailController.FinalZonasDeAlarmaNotificar(device);
						return;
					}else {
						device.getZonasluegodisparo().add(zona+";"+status+";"+hora());
						devdao.update(device);
					}
				}else {
					device.setZonasluegodisparo(new ArrayList<>());
					devdao.update(device);
				}
			}
		}
	}

	
	public static String hora(){
		Date today = Calendar.getInstance().getTime();
		return today.toString();
	}


}
