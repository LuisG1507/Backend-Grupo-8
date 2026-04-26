package pe.edu.pe.smartrent_backend.ServicesInterfaces;


import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.OwnerEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.UserEstateDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;

import java.util.List;
import java.util.Optional;

public interface IEstate {
    public void Register(Estate e);
    public Optional<Estate> listarId(Integer id);
    public void Actualizar(Estate estE);
    public List<Estate> listar();
    public void eliminar(Integer id);
    public List<Estate> filtrarInmueblesPorCiudadDistritoTipo(String city, String district, String type);
    public double amountTotal();
    public List<OwnerEstateDTO> listUsersEstate();
    public List<UserEstateDTO> listINNERJOIN(String d);
    public List<Object[]> AboveAverageRents();

}
