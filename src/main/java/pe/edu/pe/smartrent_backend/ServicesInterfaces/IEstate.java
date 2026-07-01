package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import java.util.List;
import java.util.Optional;

public interface IEstate {
    public void Register(Estate e);
    public Optional<Estate> listarId(Integer id);
    public void Actualizar(Estate estE);
    public List<Estate> listar();
    public List<Estate> listarPorUsername(String username);
    public void eliminar(Integer id);
    public List<Estate> filtrarInmueblesPorCiudadDistritoTipo(String city, String district, String type);
    public List<Object[]> listUsersEstate();
    public List<Object[]> EstateDistrict(String d);
    public List<Object[]> AboveAverageRents();

    public List<Object[]> findBestPricePerRoom();
    public List<Object[]> findDistributionByTypeAndPriceRange();




}
