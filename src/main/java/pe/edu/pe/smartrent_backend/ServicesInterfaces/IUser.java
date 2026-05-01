package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import org.apache.catalina.User;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserEnabledByRoleDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserMonthlyGrowthDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserUnverifiedWithBackgroundDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserVerificationStatsDTO;
import pe.edu.pe.smartrent_backend.Entities.Users;

import java.time.LocalDate;
import java.util.List;

public interface IUser {

    public void Register(Users user); //Registrar

    public void Update(Users user); //Modificar

    public Users listId(Integer id); //ListarId

    public List<Users> list(); //Listartodo

    public void Delete(Integer id);

    public Users BuscarPorDNI(Integer id); //Filtro Simple para buscar por DNI

    public List<Users> fyndByStatus(); //ListarVerificados

    public List<Users> userByRangeDate(LocalDate f1, LocalDate f2);

    public List<Object[]> RankingUsuariosIncidencias();

    List<Object[]> findVerificationStats();
    List<UserUnverifiedWithBackgroundDTO> findUnverifiedUsersWithBackgrounds();
    List<Object[]> findMonthlyGrowth();
    List<Object[]> findEnabledUsersByRole();
}
