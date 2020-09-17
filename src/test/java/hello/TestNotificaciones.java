package hello;

import java.util.Date;

import org.junit.Test;

import Persistence.DAO.DeviceDAO;
import Persistence.DAO.UserDAO;
import Persistence.Model.Device;
import Persistence.Model.DeviceNotification;
import Persistence.Model.User;
import mqttContexto.DevicesCoiaca;

public class TestNotificaciones {
	
	//@Test
	public void testEstablecerTiempoParaNoficacionWifi() {
		UserDAO userdao= new UserDAO();
		User user= userdao.retrieveByMail("leandrogabrielguzman@gmail.com");
		DeviceNotification notificacionSignalWifi= new DeviceNotification();
		notificacionSignalWifi.setTime(new Date().toString());
		notificacionSignalWifi.setContent("1");
		user.getNotificacionSignalWifi().add(notificacionSignalWifi);
		userdao.update(user);
		System.out.println("termino");
	}
	
	@Test
	public void testValidarTiempoEntreNotificaciones() {
		UserDAO userdao= new UserDAO();
		User user= userdao.retrieveByMail("leandrogabrielguzman@gmail.com");
		user.getNotificacionSignalWifi();
		DeviceDAO devdao = new DeviceDAO();
		Device device= devdao.retrieveBySerialNumber("DSC010000000002");
		System.out.println("Device: "+ device);
		DevicesCoiaca dev= new DevicesCoiaca();
		System.out.println("a: "+ user);
		Boolean t= dev.esNecesarioNotificar(user, device);
		System.out.println("result: "+ t);		
	}
	
}
