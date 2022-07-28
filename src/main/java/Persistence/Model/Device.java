package Persistence.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Persistence.DAO.DeviceDAO;
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
	private Map<String,String> particiones;
	private Integer mayorZonaInformada;
	private List<DeviceAlarm> alarms;
	private Boolean usedefaultbrocker;
	private List<DeviceConfiguration> deviceconfiguration;
	private Map<String,String> vista;
	private List<String> users;
	private List<String> admins;
	private String status;
	private Map<String,String> zonasObtenidas;
	private String particionactiva;
	private String calle;
	private String numero;
	private String depto;
	private String piso;
	private String localidad;
	private String codpostal;
	private String provincia;
	private String pais;
	private String tipodireccion;
	private String codigouri;
	private String uridoorman;
	private List<String> ultimaszonas;
	private List<String> zonasluegodisparo;
	private String alarmaTriggerTrouble;
	private String coddesarmado;

	
	public Device() {
		usedefaultbrocker = true;
		delete = false;
		deviceconfiguration = new ArrayList<>();
		users = new  ArrayList<>();
		admins = new ArrayList<>();
		vista = new HashMap<String,String>();
		alarmaTriggerTrouble="";
	}

	
	public String getCoddesarmado() {
		return coddesarmado;
	}

	public void setCoddesarmado(String coddesarmado) {
		this.coddesarmado = coddesarmado;
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

	public String getUridoorman() {
		return uridoorman;
	}

	public void setUridoorman(String uridoorman) {
		this.uridoorman = uridoorman;
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

	public String getCodigouri() {
		return codigouri;
	}

	public void setCodigouri(String codigouri) {
		this.codigouri = codigouri;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getTipo() {
		return tipo;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCodpostal() {
		return codpostal;
	}

	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}

	public String getTipodireccion() {
		return tipodireccion;
	}

	public void setTipodireccion(String tipodireccion) {
		this.tipodireccion = tipodireccion;
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

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public String getParticionactiva() {
		return particionactiva;
	}

	public void setParticionactiva(String particionactiva) {
		this.particionactiva = particionactiva;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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



	public Map<String, String> getZonasObtenidas() {
		return zonasObtenidas;
	}

	public void setZonasObtenidas(Map<String, String> zonasObtenidas) {
		this.zonasObtenidas = zonasObtenidas;
	}

	public Map<String, String> getParticiones() {
		return particiones;
	}

	public void setParticiones(Map<String, String> particiones) {
		this.particiones = particiones;
	}

	public Integer getMayorZonaInformada() {
		return mayorZonaInformada;
	}

	public void setMayorZonaInformada(Integer mayorZonaInformada) {
		this.mayorZonaInformada = mayorZonaInformada;
	}

	public static List<String> obtenerClientesCoiaca() {
		DeviceDAO devdao= new DeviceDAO();
		List<String> result = new ArrayList<String>();
		List<Device> devices = devdao.retrieveAllDevices();
//		DeviceConfiguration config = null;
		for(Device dev: devices) {
			if(dev.getUsedefaultbrocker() && dev.getDeviceconfiguration()!=null  && dev.getDeviceconfiguration().size()!=0) {
				String topico = dev.getDeviceconfiguration().get(0).getTopicescuchar();
				result.add(topico);		
			}
		}
		return result;
	}


	public List<String> getUltimaszonas() {
		if(this.ultimaszonas==null)
			ultimaszonas=new ArrayList<>();
		return ultimaszonas;
	}

	public void setUltimaszonas(List<String> ultimaszonas) {
		this.ultimaszonas = ultimaszonas;
	}

	public List<String> getZonasluegodisparo() {
		if(this.zonasluegodisparo==null)
			zonasluegodisparo=new ArrayList<>();
		return zonasluegodisparo;
	}

	public void setZonasluegodisparo(List<String> zonasluegodisparo) {
		this.zonasluegodisparo = zonasluegodisparo;
	}

	public String getAlarmaTriggerTrouble() {
		return alarmaTriggerTrouble;
	}

	public void setAlarmaTriggerTrouble(String alarmaTriggerTrouble) {
		this.alarmaTriggerTrouble = alarmaTriggerTrouble;
	}	
}
