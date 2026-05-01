package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundAverageDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundMonthlyDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundSourceDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundTypeFrequencyDTO;
import pe.edu.pe.smartrent_backend.Entities.UsersBackground;

import java.util.List;

public interface IUserBackground {

    public List<UsersBackground> list(); //listar
    public void Register(UsersBackground ub); //Registrar
    public void Update(UsersBackground ub); //Actualizar
    public UsersBackground listId(Integer id); //ListarId
    public void Delete(Integer id); //Eliminar
    List<UserBackgroundTypeFrequencyDTO> findMostFrequentTypes();
    List<UserBackgroundAverageDTO> findHighRiskUsers();
    List<UserBackgroundSourceDTO> findMostReportingSources();
    List<UserBackgroundMonthlyDTO> findMonthlyTrend();
}
