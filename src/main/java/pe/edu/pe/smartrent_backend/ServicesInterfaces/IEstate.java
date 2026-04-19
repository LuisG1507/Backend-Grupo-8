package pe.edu.pe.smartrent_backend.ServicesInterfaces;


import pe.edu.pe.smartrent_backend.Entities.Estate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface IEstate {
    public void Register(Estate e);
    public Optional<Estate> listarId(Integer id);
    public void Actualizar(Estate estE);
    public List<Estate> listar();
    public void eliminar(Integer id);


}
