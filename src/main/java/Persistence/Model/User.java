package Persistence.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Persistence.Mongo.MongoDBObject;



public class User extends MongoDBObject{
    
	private static final long serialVersionUID = -4346222511562336633L;
	static final public String ROLE_SUPERADMIN = "SUPERADMIN";
    static final public String ROLE_ADMIN = "ADMIN";
    static final public String ROLE_USER = "USER";
    

	private String firstname;
	private String lastname;
	private byte[] password;
	private Boolean delete;
	private String email;
	private String passCuenta;
	private Boolean cuenta_iniciada;
	private String role;
	private List<String> deviceserialnumber;
	private String cookie;
	private Map<String,Boolean> notificaciones;
	private List<String> firebasetoken;
	private String fechaCreacion;
	private String fechaUltimoIngrego;
	private Integer ingresosSinLogin;
	private List<DeviceNotification> notificacionSignalWifi;
	
	
	public List<DeviceNotification> getNotificacionSignalWifi() {
		return notificacionSignalWifi;
	}


	public void setNotificacionSignalWifi(List<DeviceNotification> notificacionSignalWifi) {
		this.notificacionSignalWifi = notificacionSignalWifi;
	}



	public String getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public String getFechaUltimoIngrego() {
		return fechaUltimoIngrego;
	}


	public void setFechaUltimoIngrego(String fechaUltimoIngrego) {
		this.fechaUltimoIngrego = fechaUltimoIngrego;
	}


	public Integer getIngresosSinLogin() {
		return ingresosSinLogin;
	}


	public void setIngresosSinLogin(Integer ingresosSinLogin) {
		this.ingresosSinLogin = ingresosSinLogin;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public User(){
		cuenta_iniciada=false;
		this.setDelete(false);
	}
	
	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

	public String getCookie() {
		return cookie;
	}


	public void setCookie(String cookie) {
		this.cookie = cookie;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public byte[] getPassword() {
		return password;
	}


	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Boolean getDelete() {
		return delete;
	}


	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getCuenta_iniciada() {
		return cuenta_iniciada;
	}

	public void setCuenta_iniciada(Boolean cuenta_iniciada) {
		this.cuenta_iniciada = cuenta_iniciada;
	}


	public String getPassCuenta() {
		return passCuenta;
	}


	public void setPassCuenta(String passCuenta) {
		this.passCuenta = passCuenta;
	}


	public List<String> getDeviceserialnumber() {
		if(deviceserialnumber == null)
			deviceserialnumber = new ArrayList<>();
		return deviceserialnumber;
	}


	public void setDeviceserialnumber(List<String> deviceserialnumber) {
		this.deviceserialnumber = deviceserialnumber;
	}

	public List<String> getFirebasetoken() {
		if(firebasetoken == null)
			firebasetoken = new ArrayList<String>();
		return firebasetoken;
	}


	public void setFirebasetoken(List<String> firebasetoken) {
		this.firebasetoken = firebasetoken;
	}
	
	public Map<String, Boolean> getNotificaciones() {
		if(notificaciones == null)
			notificaciones=new HashMap<String, Boolean>();
		return notificaciones;
	}


	public void setNotificaciones(Map<String, Boolean> notificaciones) {
		this.notificaciones = notificaciones;
	}
}
