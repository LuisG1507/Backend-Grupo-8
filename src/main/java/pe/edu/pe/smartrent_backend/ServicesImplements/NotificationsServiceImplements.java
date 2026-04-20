package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.Repositories.INotificationsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;

import java.util.List;

@Service
public class NotificationsServiceImplements implements INotifications {
    @Autowired
    private INotificationsRepository nR;

    @Override
    public void Registrar(Notifications notifications) {
        nR.save(notifications);
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
    public Notifications listId(Integer id) {
        return nR.findById(id).orElse(null);
    }

    @Override
    public void Delete(Integer id) {
        nR.deleteById(id);

    }
}
