package hello;

import java.util.Random;

import org.junit.Test;


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
