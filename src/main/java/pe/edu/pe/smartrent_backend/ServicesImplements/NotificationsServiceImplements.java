package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsTypeDTO;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.Repositories.INotificationsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;

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
    public Notifications listIde(Integer id) {
        return nR.findById(id).orElse(null);
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
}
