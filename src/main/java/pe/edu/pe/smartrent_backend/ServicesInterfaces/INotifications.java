package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import java.util.List;
import java.util.Optional;

public interface INotifications {
    //RegistrarNotifaciones
    public Notifications Registrar (Notifications notifications);
    //ActualizarN
    public void Update (Notifications notifications);

    public Notifications listIde(Integer id);

    //Listar
    public List<Notifications > list();
    //Listar por Id
    public Optional<Notifications>listId(int id);
    //Eliminar
    public void Delete (Integer id);

}
