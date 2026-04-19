package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

import java.util.List;
import java.util.Optional;

public interface IModels3D {

    public void registrar(Models3D models3D);
    public Optional<Models3D> listarId(Integer id);
    public void actualizar(Models3D models3D);
    public List<Models3D> Listar();
    public void eliminar(Integer id);

}
