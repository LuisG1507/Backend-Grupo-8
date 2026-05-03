package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsTypeDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationReadRateDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationSecurityAlertDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationTypeMonthlyDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationUnreadUserDTO;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.Repositories.INotificationsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<NotificationsTypeDTO> getCountByType() {
        List<Object[]> lista = nR.getCountByTypeRaw();
        List<NotificationsTypeDTO> listaDTO = new ArrayList<>();

        for (Object[] columna : lista) {
            NotificationsTypeDTO dto = new NotificationsTypeDTO();
            dto.setType((String) columna[0]);
            dto.setQuantity((Long) columna[1]);
            listaDTO.add(dto);
        }
        return listaDTO;
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
