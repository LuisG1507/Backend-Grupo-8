package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.*;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IModels3D {

    public void registrar(Models3D models3D);
    public Optional<Models3D> listarId(Integer id);
    public void actualizar(Models3D models3D);
    public List<Models3D> Listar();
    public void eliminar(Integer id);
    public List<Models3D> buscarPorEstado(String state);
    public List<Models3D> buscarPorFecha(LocalDate fecha);
    public List<Object[]> modelosConUbicacion();
    public List<Object[]> inmueblesConModelo();
    public List<Object[]> findEstatesWithoutModel();
    public List<Object[]> findCitiesWithMostActiveModels();
    public List<Object[]> findStateRate();
    public List<Object[]> findEstatesWithCriticalRiskPoints();

}
