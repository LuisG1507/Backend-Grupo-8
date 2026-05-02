package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Messages;

import java.util.List;

@Repository
public interface IMessagesRepository extends JpaRepository <Messages, Integer> {

    //QuerySimple
    List<Messages> findByStatus(String status);

    //QueryTomaDecisiones NUEVO
    @Query("SELECT m.dateSent, COUNT(m.idMessage) FROM Messages m GROUP BY m.dateSent ORDER BY m.dateSent DESC")
    List<Object[]> findMessagesActivityByDate();

}
