package hello;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class SendNotifications {

	
//	GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
//	FirebaseOptions options = new FirebaseOptions.Builder()
//	    .setCredentials(credentials)
//	    .setProjectId("cdash-1274d")
//	    .build();
//	FirebaseApp.initializeApp(options);
//
//	Firestore db = FirestoreClient.getFirestore();
	
	public void enviarNotficacionFB(){
	try {
		JSONObject message = new JSONObject("{\"notification\": { \"title\": \"Oferta!\", \"body\": \"Tienes un 40% de descuento en todos nuestros productos.\", \"icon\": \"/images/manifest/icon-96x96.png\" }, \"to\" : \"[TOKEN-USUARIO]\" }");
		String messageFinal = message.toString();
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty ("Authorization", "key=AAAAVb7Xn5w:APA91bGkFdLFPADu3VjYGpLwmBWULzZJ8bAmk5rBwgZEWZi1NgcTQzpcmc6bdZQsC-IIuWOmGEuRW5zsq8HotXFrxa1XAr6ETIIEbL7sb-UvbngXvBB2PqBrSF7--9LilcvVJBx0Y6Co");
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);
		try(OutputStream os = con.getOutputStream()) {
		    byte[] input = messageFinal.getBytes("utf-8");
		    os.write(input, 0, input.length);           
		}
		int cod_status = con.getResponseCode();
		String status = con.getResponseMessage();
		System.out.println("RESPUESTA: "+ status+": "+cod_status);
		
	} catch (Exception e) {
		System.out.println("fallo_envio_de la notificacion");
		e.printStackTrace();

	}
	}
	
	
}
