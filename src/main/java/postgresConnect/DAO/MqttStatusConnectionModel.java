package postgresConnect.DAO;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BrokerConnectionStatus")
public class MqttStatusConnectionModel {
	public static final String ONLINE_BROKER="online";
	public static final String OFFLINE_BROKER="offline";
	public static final Integer ONLINE_BROKER_INT=1;
	public static final Integer OFFLINE_BROKER_INT=0;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notification_id;
    private String broker;
    private Integer brokerint;
    private String brokerstatus;
    private String created_on;
    
    
    public MqttStatusConnectionModel() {
    	
    }


	public long getNotification_id() {
		return notification_id;
	}


	public void setNotification_id(long notification_id) {
		this.notification_id = notification_id;
	}


	public String getBroker() {
		return broker;
	}


	public void setBroker(String broker) {
		this.broker = broker;
	}


	public Integer getBrokerint() {
		return brokerint;
	}


	public void setBrokerint(Integer brokerint) {
		this.brokerint = brokerint;
	}


	public String getBrokerstatus() {
		return brokerstatus;
	}


	public void setBrokerstatus(String brokerstatus) {
		this.brokerstatus = brokerstatus;
	}


	public String getCreated_on() {
		return created_on;
	}


	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

    
    
}
