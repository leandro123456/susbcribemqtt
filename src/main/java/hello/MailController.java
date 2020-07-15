package hello;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;


public class MailController {

	public void enviarNotificacion(String usermail, String mensaje) {
		//armado del mail
		String cabecera = "<HTML><BODY><br/> <br/>";
		String body= "<h1>Ha recibido una Notificacion </h1> <br/> <h3>"
				+ mensaje
				+ "</h3> <br/>";
		String pie = "<br/> <br/> <footer><p> 2020 - cDash</p></footer></BODY></HTML>";
		String formulario = String.format("%s%s%s%s", cabecera, body, "<br/> <br/>", pie);
		sendMail(formulario, usermail);
	}
	
	
	
	public static void sendMail(String Mensaje,String destino) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("iot@qliq.com.ar", "nMvJRVdqb0DXlgpPVJnr");
			}
		});
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("cDash"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destino));
			message.setSubject("[cDash]: Notificacion");
			message.setText(Mensaje,"ISO-8859-1","html");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com","iot@cdash.space", "nMvJRVdqb0DXlgpPVJnr");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
			System.out.println("Su mensaje se envio correctamente");

		} catch (MessagingException e) {
			System.out.println("fallo el envio del mensaje");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}



	public static void FinalZonasDeAlarmaNotificar(Device device) {
		DeviceDAO devdao=new DeviceDAO();
		String[] vector = device.getAlarmaTriggerTrouble().split(Pattern.quote(";"));
		Boolean status=Boolean.parseBoolean(vector[0]);
		String hora= vector[1];
		
		
		//ENVIAR MAIL
		for(String usermail: listaDeUsuarios(device)) {		
			String cabecera = "<HTML><head>\n" + 
					"  <style>\n" + 
					"  table {\n" + 
					"    width:100%;\n" + 
					"  }\n" + 
					"  table, th, td {\n" + 
					"    border: 1px solid black;\n" + 
					"    border-collapse: collapse;\n" + 
					"  }\n" + 
					"  th, td {\n" + 
					"    padding: 15px;\n" + 
					"    text-align: left;\n" + 
					"  }\n" + 
					"  tr:nth-child(even) {\n" + 
					"    background-color: #eee;\n" + 
					"  }\n" + 
					"  tr:nth-child(odd) {\n" + 
					"   background-color: #fff;\n" + 
					"  }\n" + 
					"  th {\n" + 
					"    background-color: black;\n" + 
					"    color: white;\n" + 
					"  }\n" + 
					"  </style>\n" + 
					"  </head><BODY><br/> <br/>";
			String tablaprevia="";
			for(int i=0; i<device.getZonasluegodisparo().size(); i++) {
				tablaprevia=tablaprevia+"<tr>";
				String[] vector1 = device.getZonasluegodisparo().get(i).split(Pattern.quote(";"));
				tablaprevia=tablaprevia+"<td>"+vector1[0]+"</td>";
				tablaprevia=tablaprevia+"<td>"+vector1[1]+"</td>";
				tablaprevia=tablaprevia+"<td>"+vector1[2]+"</td>";
				tablaprevia=tablaprevia+"</tr>";
			}	
			String body= "<h1>Su alarma paso a Estado"+"Trigger"+" </h1> <br/> "
					+ "<h3>Se agregan los 10 regostros previos a que se ejecute su Alarma</h3> "
					+ "<table >\n" + 
					"  <tr>\n" + 
					"    <th>Zona</th>\n" + 
					"    <th>Estado</th> \n" + 
					"    <th>Fecha</th>\n" + 
					"  </tr>\n" + tablaprevia+ 
					"</table>"
					+ "<br/>";
			String pie = "<br/> <br/> <footer><p> Dash</p></footer></BODY></HTML>";
			String formulario = String.format("%s%s%s%s", cabecera, body, "<br/> <br/>", pie);
			MailController.sendMail(formulario, "leandrogabrielguzman@gmail.com");
		}

		//BLANQUEAR LISTA
		device.setZonasluegodisparo(null);
		device.setAlarmaTriggerTrouble(null);
		devdao.update(device);
	}



	private static List<String> listaDeUsuarios(Device device) {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add(new String(Base64.getDecoder().decode(device.getUserowner().getBytes())));
		destinatarios.addAll(device.getUsers());
		destinatarios.addAll(device.getAdmins());
		return destinatarios;
		
	}
}
