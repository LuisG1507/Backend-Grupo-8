package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.Entities.Messages;

import java.util.List;

public interface IMessages<list> {
    //RegistrarMensajes
    public void Registrar (Messages messages);
    //Actualizar
    public void Update (Messages messages);
    //Listar
    public List<Messages > list();
    //Listar por Id
    public Messages listId(Integer id);
    //Eliminar
    public void Delete (Integer id);

}
