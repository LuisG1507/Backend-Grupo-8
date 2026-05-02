package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Models3DActiveByCityDTO;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Models3DCriticalRiskDTO;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Models3DNoModelEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Models3DStateRateDTO;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

import java.util.List;
import java.util.Optional;

public interface IModels3D {

    public void registrar(Models3D models3D);
    public Optional<Models3D> listarId(Integer id);
    public void actualizar(Models3D models3D);
    public List<Models3D> Listar();
    public void eliminar(Integer id);
    List<Models3D> buscarPorEstado(String state);
    List<Models3D> buscarPorFecha(String fecha);
    List<Object[]> modelosConUbicacion();
    List<Object[]> inmueblesConModelo();

    List<Object[]> findEstatesWithoutModel();
    List<Object[]> findCitiesWithMostActiveModels();
    List<Object[]> findStateRate();
    List<Object[]> findEstatesWithCriticalRiskPoints();

}
