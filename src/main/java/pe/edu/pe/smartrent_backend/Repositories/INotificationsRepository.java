package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Notifications;

import java.util.List;

@Repository
public interface INotificationsRepository extends JpaRepository <Notifications, Integer> {
   //QuerySimple
    List<Notifications> findByReadFalse();

    //QueryTomaa
    @Query("SELECT n FROM Notifications n WHERE n.type = 'SEGURIDAD' AND n.createdDate >= CURRENT_DATE - 7")
    List<Notifications> findRecentSecurityAlertsJPQL();

}
