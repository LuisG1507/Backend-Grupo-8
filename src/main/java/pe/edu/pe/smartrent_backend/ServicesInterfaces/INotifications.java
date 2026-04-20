package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Entities.Notifications;

import java.util.List;

public interface INotifications {
    //RegistrarNotifaciones
    public void Registrar (Notifications notifications);
    //ActualizarN
    public void Update (Notifications notifications);
    //Listar
    public List<Notifications > list();
    //Listar por Id
    public Notifications listId(Integer id);
    //Eliminar
    public void Delete (Integer id);

}
