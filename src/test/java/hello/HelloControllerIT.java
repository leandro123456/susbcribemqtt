package hello;

import java.net.URI;
import java.util.Random;

import org.junit.Test;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;

//import java.net.URL;

//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {
	
	
//	@Test
	public void generarRandomLongitud9(){
		for(int i=0; i<10 ;i++){
			Random numsala = new Random();
			String val = numsala.nextInt()+"";
			val=val.replace("-", "");
			if(val.length()>9)
				val=val.substring(0, 9);
			System.out.println("num: "+ val);
		}
	}
	
//	@Test
	public void testSendNoficacionFirebase() {
		FirebaseController fire = new FirebaseController();
		fire.enviarNotificacion("leandrogabrielguzman@gmail.com", "TEST envio de Notificacion");
		System.out.println("termino");
	}
	
	
	//@Test
	public void testSendNotificationTwo() {
		
		
		try {
			String token= "cCmHZ2HAiJcYytf79hN4cS:APA91bFoZUERcJKb-lW5kj1V8STpYzB3Ucd-FkSPO6kVLL3Qqryq1VwDGAP1RFA36eYwzxbCTFYd0g_tjjQPUZvspY08gsyqX9FXd9aeTuBKhvGR2Er8pvMrEoEMh-QJIITBimuSNbjh";
			URI uri= new URI("https://www.facebook.com");
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body1 = RequestBody.create(mediaType, 
					"{\"notification\":{ "
							+ "\"title\": \"cDash Notificacion\", "
							+ "\"score\": \"5x1\","
							+ "\"time_to_live\": 5,"
							+ "\"click_action\": \""+uri+"\","
							+ "\"body\": \"This is a Firebase Cloud Messaging Device Group Message!\","
							+ "},"
							+ "\"to\":\""+token+"\""
							+ "}");
//			RequestBody body2 = RequestBody.create(mediaType, ""
//					+ "{\"to\":\"/"	+ token
//					+ "\",\"notification\": "
//					+ "{\"title\": \""+"cDash Notificacion"+ "\","
//					+ "\"body\":\""+"CASA CASA"+ "\","
//					+ "\"click_action\": \""+uri+"\""
//					+ "}}");
			
			System.out.println("este es el mensaje que envio: "+ body1);	

			Request request = new Request.Builder()
					.url("https://fcm.googleapis.com/fcm/send")
					.method("POST", body1)
					.addHeader("Authorization","key=AIzaSyAmq0sl80gwzNIj7b74Y-QrpwpBSe-itWI")
					.addHeader("Content-Type","application/json")
					.build();
			Response response = client.newCall(request).execute();
			System.out.println(response.code());
			String responseString= response.body().string();
			System.out.println(responseString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Message allPlatformsMessage() {
	    // [START multi_platforms_message]
	    Message message = Message.builder()
	        .setNotification(new Notification(
	            "$GOOG up 1.43% on the day",
	            "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day."))
	        .setAndroidConfig(AndroidConfig.builder()
	            .setTtl(3600 * 1000)
	            .setNotification(AndroidNotification.builder()
	                .setIcon("stock_ticker_update")
	                .setColor("#f45342")
	                .build())
	            .build())
	        .setApnsConfig(ApnsConfig.builder()
	            .setAps(Aps.builder()
	                .setBadge(42)
	                .build())
	            .build())
	        .setTopic("industry-tech")
	        .build();
	    // [END multi_platforms_message]
	    return message;
	  }
	
	
  //  @LocalServerPort
  //  private int port;

  //  private URL base;

  //  @Autowired
  //  private TestRestTemplate template;

  //  @Before
  //  public void setUp() throws Exception {
  //      this.base = new URL("http://localhost:" + port + "/");
  //  }

    //@Test
 //   public void getHello() throws Exception {
 //       ResponseEntity<String> response = template.getForEntity(base.toString(),
 //               String.class);
 //       assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
 //   }
}
