package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import pe.edu.pe.smartrent_backend.Entities.Role;

import java.util.List;

public interface IRole {


    public void Register(Role role); //Registrar

    public void Update(Role role); //Modificar

    public Role listId(Integer id); //ListarId

    public List<Role> list(); //Listartodo

    public void Delete(Integer id); //Eliminar


    //Decisiones


    public List<Object[]> RDecision1(); // Distribución de roles en la plataforma con porcentaje
    public List<Object[]> RDecision2(); // Usuarios con más de un rol asignado
}
