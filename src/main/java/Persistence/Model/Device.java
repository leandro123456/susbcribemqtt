package Persistence.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.codecs.pojo.annotations.BsonProperty;

import Persistence.Mongo.MongoDBObject;



public class Device extends MongoDBObject{

	private static final long serialVersionUID = -4346222511562336633L;
	static final public String TERMOMETRO = "termometro";
	static final public String SONOFF = "sonoff";
	static final public String ALARMA = "alarma";
	
	private String name;
	private String userowner;
	private String serialnumber;
	private String tipo;
	private String description;
	private byte[] password;
	private List<byte[]> historyPassword;
	private Boolean delete;
	private String role;
	private String timerString;
	private DeviceNotification lastnotification;
	private Integer particiones;
	private Integer mayorZonaInformada;
	private List<DeviceAlarm> alarms;
	private Boolean usedefaultbrocker;
	private List<DeviceConfiguration> deviceconfiguration;
	private Map<String,String> vista;
	private List<String> users;
	private List<String> admins;

	
	public Device() {
		usedefaultbrocker = true;
		delete = false;
		deviceconfiguration = new ArrayList<>();
		users = new  ArrayList<>();
		admins = new ArrayList<>();
		vista = new HashMap<String,String>();
	}

	public String getUserowner() {
		return userowner;
	}

	public void setUserowner(String userowner) {
		this.userowner = userowner;
	}

	public Boolean getUsedefaultbrocker() {
		return usedefaultbrocker;
	}

	public void setUsedefaultbrocker(Boolean usedefaultbrocker) {
		this.usedefaultbrocker = usedefaultbrocker;
	}

	public List<DeviceConfiguration> getDeviceconfiguration() {
		return deviceconfiguration;
	}

	public void setDeviceconfiguration(List<DeviceConfiguration> deviceconfiguration) {
		this.deviceconfiguration = deviceconfiguration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<byte[]> getHistoryPassword() {
		return historyPassword;
	}

	public void setHistoryPassword(List<byte[]> historyPassword) {
		this.historyPassword = historyPassword;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public Map<String, String> getVista() {
		return vista;
	}

	public void setVista(Map<String, String> vistaPorUsuario) {
		this.vista = vistaPorUsuario;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getAdmins() {
		return admins;
	}

	public void setAdmins(List<String> admins) {
		this.admins = admins;
	}


	public String getTimerString() {
		return timerString;
	}

	public void setTimerString(String timerString) {
		this.timerString = timerString;
	}
	
	public DeviceNotification getLastnotification() {
		return lastnotification;
	}

	public void setLastnotification(DeviceNotification lastnotification) {
		this.lastnotification = lastnotification;
	}

	public List<DeviceAlarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<DeviceAlarm> alarms) {
		this.alarms = alarms;
	}

	public String getUserRole(String userTarget) {
		if(userowner.equals(userTarget))
			return User.ROLE_SUPERADMIN;
		if(users.contains(userTarget))
			return User.ROLE_USER;
		if(admins.contains(userTarget))
			return User.ROLE_ADMIN;
		else
			return "fallo";
	}

	public Integer getParticiones() {
		return particiones;
	}

	public void setParticiones(Integer particiones) {
		this.particiones = particiones;
	}

	public Integer getMayorZonaInformada() {
		return mayorZonaInformada;
	}

	public void setMayorZonaInformada(Integer mayorZonaInformada) {
		this.mayorZonaInformada = mayorZonaInformada;
	}
	

//	public HashMap<Integer, Integer> getZonas() {
//		return zonas;
//	}
//
//	public void setZonas(HashMap<Integer, Integer> zonas) {
//		this.zonas = zonas;
//	}
	
}
