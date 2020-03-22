package hello;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import Persistence.DAO.UserDAO;
import Persistence.Model.User;

public class FirebaseController {
	public static final String URI_FIREBASE= "https://fcm.googleapis.com/fcm/send";
	private UserDAO userdao = new UserDAO();
	
	
	
	public void enviarNotificacion(String username, String body) {
		try {
			
			//String userdeco = new String(Base64.getDecoder().decode(username.getBytes()));
			System.out.println("****************username que busco notification: "+ username);
			User user = userdao.retrieveByMail(username);
			List<String> tokenAborrar= new ArrayList<String>();
			if(user!=null && user.getFirebasetoken()!=null) {
			for(String token: user.getFirebasetoken()) {
				System.out.println("cantidad de tokens notification: "+ user.getFirebasetoken().size());
				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body1 = RequestBody.create(mediaType, "{\"notification\":{ "
						+ "\"title\": \"cDash Notificacion\", "
						+ "\"body\": \""+body+"\","
						+ " \"icon\": \"/images/manifest/icon-96x96.png\" }, "
						+ "\"to\" : \""+token+"\""
								+ "}");
				
				Request request = new Request.Builder()
						.url("https://fcm.googleapis.com/fcm/send")
						.method("POST", body1)
						.addHeader("Authorization", "key=AIzaSyAmq0sl80gwzNIj7b74Y-QrpwpBSe-itWI")
						.addHeader("Content-Type", "application/json")
						.build();
				Response response = client.newCall(request).execute();
				AnalizarCodigoRespuesta(response.code());
				String responseString= response.body().string();
				Boolean borrartoken= verificarRespuesta(responseString, user, token);
				if(borrartoken)
					tokenAborrar.add(token);
			}
			user.getFirebasetoken().removeAll(tokenAborrar);
			userdao.update(user);
			System.out.println("token actualizados");
			}
		} 
		catch (Exception e) {
			System.out.println("Fallo el envio del mensaje Firebase: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void enviarNotificacionDoorman(String username, String body, URI uri) {
		enviarNotificacion(username, "este es un mensaje de prueba");
		try {
			System.out.println("****************username que busco: "+ username);
			User user = userdao.retrieveByMail(username);
			List<String> tokenAborrar= new ArrayList<String>();
			if(user!=null && user.getFirebasetoken()!=null) {
			for(String token: user.getFirebasetoken()) {
				OkHttpClient client = new OkHttpClient();
				MediaType mediaType = MediaType.parse("application/json");

				RequestBody body2 = RequestBody.create(mediaType, ""
						+ "{\"to\":\"/"	+ token
						+ "\",\"notification\": "
						+ "{\"title\": \""+"cDash Notificacion"+ "\","
						+ "\"body\":\""+body+ "\","
						+ "\"click_action\": \""+uri+"\""
						+ "}}");
				
				Request request = new Request.Builder()
						.url("https://fcm.googleapis.com/fcm/send")
						.method("POST", body2)
						.addHeader("Authorization", "key=AIzaSyAmq0sl80gwzNIj7b74Y-QrpwpBSe-itWI")
						.addHeader("Content-Type", "application/json")
						.build();
				Response response = client.newCall(request).execute();
				AnalizarCodigoRespuesta(response.code());
				String responseString= response.body().string();
				Boolean borrartoken= verificarRespuesta(responseString, user, token);
				if(borrartoken)
					tokenAborrar.add(token);
			}
			user.getFirebasetoken().removeAll(tokenAborrar);
			userdao.update(user);
			System.out.println("token actualizados");
			}
		} 
		catch (Exception e) {
			System.out.println("Fallo el envio del mensaje Firebase: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	private void AnalizarCodigoRespuesta(int code) {
		System.out.println("llego este codigo: "+ code);
		
	}
	private Boolean verificarRespuesta(String respuesta, User user, String token) {
		System.out.println("respuesta: "+ respuesta);
		String parte= respuesta.substring(0, respuesta.indexOf("results")-2)+"}";
		JSONObject jsonp1 = new JSONObject(parte);
		if(jsonp1.has("failure") && jsonp1.get("failure").equals(1)) {
			System.out.println("Fallo en el envio del mensaje, borrarlo de la base");
			String parte2= respuesta.substring(respuesta.indexOf("results")+10, respuesta.length()-1);
			JSONObject jsonp2 = new JSONObject(parte2);
			if(jsonp2.has("error")) {
				System.out.println("Status de Error: "+ jsonp2.get("error"));
			}
			return true;
		}
		return false;
	}


}
