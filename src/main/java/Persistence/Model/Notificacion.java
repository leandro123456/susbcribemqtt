package Persistence.Model;

import java.util.ArrayList;
import java.util.List;

import Persistence.Mongo.MongoDBObject;


public class Notificacion extends MongoDBObject{
	private static final long serialVersionUID = -4346222511562336633L;
	//constantes coiaca
	static final public String DISARMED="disarmed";
	static final public String ARMED_HOME="armed_home";
	static final public String ARMED_ALL ="armed_all";
	static final public String TRIGERED = "triggered";
	
	//comparador
	static final public String CONDICION_ARMADO="condicion_armado";
	static final public String CONDICION_DISPARADO="condicion_disparado";
	
	
	//condicion
	static final public String IGUAL = "igual";
	static final public String MAYOR = "mayor";
	static final public String MENOR = "menor";
	
	//consecuencia
	static final public String ENVIAR_MAIL = "enviar_mail";
	static final public String ENVIAR_SMS = "enviar_sms";
	static final public String ENVIAR_WHATSAPP = "enviar_whatsapp";
	static final public String ENCENDER = "enceder";
	static final public String APAGAR = "apagar";
	
	//estado
	static final public String ACTIVAR = "activar";
	static final public String DESACTIVAR = "desactivar";
	
	private String dispositivo;
	private String user;
	private String condicion;
	private String estado;
	private List<String> consecuencia;
	
	
	
	public Notificacion(){
	}



	public String getDispositivo() {
		return dispositivo;
	}



	public void setDispositivo(String dispositivo) {
		this.dispositivo = dispositivo;
	}



	public String getUsuario() {
		return user;
	}



	public List<String> getConsecuencia() {
		return consecuencia;
	}



	public void setConsecuencia(List<String> consecuencia) {
		this.consecuencia = consecuencia;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public void setUsuario(String usuario) {
		this.user = usuario;
	}



	public String getCondicion() {
		return condicion;
	}



	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	
}
