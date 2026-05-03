package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationReadRateDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationSecurityAlertDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationTypeMonthlyDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationUnreadUserDTO;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.Repositories.INotificationsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationsServiceImplements implements INotifications {
    @Autowired
    private INotificationsRepository nR;

    @Override
    public Notifications Registrar(Notifications notifications) {
        return nR.save(notifications);
    }
    @Override
    public void Update(Notifications notifications) {
        nR.save(notifications);
    }
    @Override
    public List<Notifications> list() {
        return nR.findAll();
    }

    @Override
    public Optional<Notifications> listId(int id) {
        return nR.findById(id);
    }

    @Override
    public void Delete(Integer id) {
        nR.deleteById(id);

    }

    @Override
    public List<Notifications> buscarNoLeidos() {
        return nR.findByReadFalse();
    }

    @Override
    public List<Notifications> findRecentSecurityAlertsJPQL() {
        return nR.findRecentSecurityAlertsJPQL();
    }

    @Override
    public List<Object[]> findReadRateByType() {
        return nR.findReadRateByType();
    }

    @Override
    public List<Object[]> findUsersWithMostUnreadNotifications() {
        return nR.findUsersWithMostUnreadNotifications();
    }

    @Override
    public List<Object[]> findMostGeneratedTypesLastMonth() {
        return nR.findMostGeneratedTypesLastMonth();
    }

    @Override
    public List<Object[]> findDaysWithMostSecurityAlerts() {
        return nR.findDaysWithMostSecurityAlerts();
    }
}
