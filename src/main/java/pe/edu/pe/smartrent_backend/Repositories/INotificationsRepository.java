package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsTypeDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationReadRateDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationSecurityAlertDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationTypeMonthlyDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationUnreadUserDTO;
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

    @Query("SELECT n FROM Notifications n WHERE n.type = 'SEGURIDAD' AND n.createdDate >= LOCAL DATE - 7 day")
    List<Notifications> findRecentSecurityAlertsJPQL();

    @Query(value = "SELECT type,\n" +
            "       COUNT(*) AS total,\n" +
            "       SUM(CASE WHEN read = true THEN 1 ELSE 0 END) AS leidas,\n" +
            "       ROUND(SUM(CASE WHEN read = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS tasa_lectura\n" +
            "FROM notification\n" +
            "GROUP BY type\n" +
            "ORDER BY tasa_lectura ASC", nativeQuery = true)
    List<Object[]> findReadRateByType();

    @Query(value = "SELECT u.name, u.last_name, COUNT(n.id_notification) AS pendientes\n" +
            "FROM notification n\n" +
            "INNER JOIN users u ON n.id_user = u.id_user\n" +
            "WHERE n.read = false\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY pendientes DESC", nativeQuery = true)
    List<Object[]> findUsersWithMostUnreadNotifications();

    @Query(value = "SELECT type, COUNT(*) AS total\n" +
            "FROM notification\n" +
            "WHERE created_date >= CURRENT_DATE - INTERVAL '30 days'\n" +
            "GROUP BY type\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> findMostGeneratedTypesLastMonth();

    @Query(value = "SELECT created_date, COUNT(*) as total " +
            "FROM notification " +
            "WHERE UPPER(type) = 'SEGURIDAD' " +
            "GROUP BY created_date " +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> findDaysWithMostSecurityAlerts();

}
