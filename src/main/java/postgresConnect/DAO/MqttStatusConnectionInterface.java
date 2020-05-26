package postgresConnect.DAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface MqttStatusConnectionInterface extends CrudRepository<MqttStatusConnectionModel,Long>{
	

}
