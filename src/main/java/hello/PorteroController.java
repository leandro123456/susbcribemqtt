package hello;

import java.net.URI;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hello.util.ErrorInfo;

@RestController
public class PorteroController {
	
	@RequestMapping("doorman/{user}")
	public ModelAndView enviarNotificacionPortero(@PathVariable String user) {
		//envio de ventana de portero al solicitante
		//envio de notificacion al usuario
		String numsala= generarrandomfijo();
		String uriv = "https://appr.tc/r/"+numsala;
		try {
			final URI uri = new URI(uriv);
			FirebaseController fire = new FirebaseController();
			fire.enviarNotificacionDoorman(user, "Llego una notificacion a su servicio de porteria. Pulse en la notificacion para atenderlo ", uri);

		} catch (Exception e) {
			System.out.println("ERROR DOORMAN!!!!");
			return new ModelAndView("redirect:http://localhost:8080/doorman/error");
		}
		System.out.println("acepto doorman!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return new ModelAndView("redirect:"+uriv);
	}
	

	@RequestMapping("doorman/error") 
	public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        // get spring errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> errorMessage.append(f.getField() + " " + f.getDefaultMessage() +  " "));

        // return error info object with standard json
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), errorMessage.toString(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
}

	
	
	
	private String generarrandomfijo() {
		Random numsala = new Random();
		String val = numsala.nextInt()+"";
		val=val.replace("-", "");
		if(val.length()>9)
			val=val.substring(0, 9);
		System.out.println("num: "+ val);
		return val;
	}
}