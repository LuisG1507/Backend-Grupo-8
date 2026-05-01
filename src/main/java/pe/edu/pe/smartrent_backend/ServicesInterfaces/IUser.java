package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;
import java.util.List;

public interface IUser {

    public void Register(User user); //Registrar

    public void Update(User user); //Modificar

    public User listId(Integer id); //ListarId

    public List<User> list(); //Listartodo

    public void Delete(Integer id);

    public User BuscarPorDNI(Integer id); //Filtro Simple para buscar por DNI

    public List<User> fyndByStatus(); //ListarVerificados

    public List<User> userByRangeDate(LocalDate f1, LocalDate f2);

    public List<Object[]> RankingUsuariosIncidencias();
}
