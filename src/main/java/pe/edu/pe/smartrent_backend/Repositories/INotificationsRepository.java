package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsTypeDTO;
import pe.edu.pe.smartrent_backend.Entities.Notifications;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface INotificationsRepository extends JpaRepository <Notifications, Integer> {
   //QuerySimple
    List<Notifications> findByReadFalse();

    //QueryTomaa
    @Query("SELECT n.type, COUNT(n.idNotification) FROM Notifications n GROUP BY n.type")
    List<Object[]> getCountByTypeRaw();
}
