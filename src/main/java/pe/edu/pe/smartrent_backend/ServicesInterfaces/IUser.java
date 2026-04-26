package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import org.apache.catalina.User;
import pe.edu.pe.smartrent_backend.Entities.Users;

import java.util.List;

public interface IUser {

    public void Register(Users user); //Registrar

    public void Update(Users user); //Modificar

    public Users listId(Integer id); //ListarId

    public List<Users> list(); //Listartodo

    public void Delete(Integer id);

    public Users BuscarPorDNI(Integer id); //Filtro Simple para buscar por DNI


}
