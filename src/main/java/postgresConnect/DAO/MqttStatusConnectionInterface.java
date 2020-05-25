package postgresConnect.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MqttStatusConnectionInterface extends JpaRepository<MqttStatusConnectionModel,Long>{
	

}
