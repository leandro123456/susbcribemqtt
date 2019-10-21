package Persistence.Model;

import Persistence.Mongo.MongoDBObject;

public class DeviceDefaultConfiguration extends MongoDBObject {

	private static final long serialVersionUID = -4346222511562336633L;
	private String name;
	private String version;
	private String iphostescuchar;
	private String portescuchar;
	private Boolean usesslescuchar;
	private String userescuchar;
	private String passescuchar;
	private String topicescuchar;
	private String topicescribir;
//	private String topicescucharEstado;
//	private String topicescucharRed;
	
	private String userescucharremote;
	private String passescucharremote;
	private String iphostescucharremote;
	private String portescucharremote;
	private Boolean usesslescucharremote;
	private String topicescucharremote;
	private String topicescribirremote;

	
	
	public DeviceDefaultConfiguration() {
	}

	public DeviceDefaultConfiguration(String serial) {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserescuchar() {
		return userescuchar;
	}

	public void setUserescuchar(String userescuchar) {
		this.userescuchar = userescuchar;
	}

	public String getPassescuchar() {
		return passescuchar;
	}

	public void setPassescuchar(String passescuchar) {
		this.passescuchar = passescuchar;
	}

	public String getIphostescuchar() {
		return iphostescuchar;
	}

	public void setIphostescuchar(String iphostescuchar) {
		this.iphostescuchar = iphostescuchar;
	}

	public String getPortescuchar() {
		return portescuchar;
	}

	public void setPortescuchar(String portescuchar) {
		this.portescuchar = portescuchar;
	}

	public Boolean getUsesslescuchar() {
		return usesslescuchar;
	}

	public void setUsesslescuchar(Boolean usesslescuchar) {
		this.usesslescuchar = usesslescuchar;
	}

	public String getUserescucharremote() {
		return userescucharremote;
	}

	public void setUserescucharremote(String userescucharremote) {
		this.userescucharremote = userescucharremote;
	}

	public String getPassescucharremote() {
		return passescucharremote;
	}

	public void setPassescucharremote(String passescucharremote) {
		this.passescucharremote = passescucharremote;
	}

	public String getIphostescucharremote() {
		return iphostescucharremote;
	}

	public void setIphostescucharremote(String iphostescucharremote) {
		this.iphostescucharremote = iphostescucharremote;
	}

	public String getPortescucharremote() {
		return portescucharremote;
	}

	public void setPortescucharremote(String portescucharremote) {
		this.portescucharremote = portescucharremote;
	}

	public Boolean getUsesslescucharremote() {
		return usesslescucharremote;
	}

	public void setUsesslescucharremote(Boolean usesslescucharremote) {
		this.usesslescucharremote = usesslescucharremote;
	}
	
	public String getTopicescuchar() {
		return topicescuchar;
	}

	public void setTopicescuchar(String topicescuchar) {
		this.topicescuchar = topicescuchar;
	}

	public String getTopicescribir() {
		return topicescribir;
	}

	public void setTopicescribir(String topicescribir) {
		this.topicescribir = topicescribir;
	}

	public String getTopicescucharremote() {
		return topicescucharremote;
	}

	public void setTopicescucharremote(String topicescucharremote) {
		this.topicescucharremote = topicescucharremote;
	}

	public String getTopicescribirremote() {
		return topicescribirremote;
	}

	public void setTopicescribirremote(String topicescribirremote) {
		this.topicescribirremote = topicescribirremote;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

//	public String getTopicescucharEstado() {
//		return topicescucharEstado;
//	}
//
//	public void setTopicescucharEstado(String topicescucharEstado) {
//		this.topicescucharEstado = topicescucharEstado;
//	}
//
//	public String getTopicescucharRed() {
//		return topicescucharRed;
//	}
//
//	public void setTopicescucharRed(String topicescucharRed) {
//		this.topicescucharRed = topicescucharRed;
//	}


}
