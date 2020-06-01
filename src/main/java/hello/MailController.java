package hello;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
}
