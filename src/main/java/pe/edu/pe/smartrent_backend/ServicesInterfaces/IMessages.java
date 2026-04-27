package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.Entities.Messages;

import java.util.List;
import java.util.Optional;

public interface IMessages {
    //RegistrarMensajes
    public Messages Registrar (Messages messages);
    //Actualizar
    public void Update (Messages messages);
    //Listar
    public List<Messages > list();
    //Listar por Id
    public Optional<Messages> listId(int id);
    //Eliminar
    public void Delete (Integer id);

    //QuerySimple
    public List<Messages>findByStatus(String status);
    //QueryToma
    public List<Messages>findUrgentMessagesWithUserJPQL();

}
