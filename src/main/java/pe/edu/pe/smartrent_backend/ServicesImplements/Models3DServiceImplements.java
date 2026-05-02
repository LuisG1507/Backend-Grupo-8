package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.*;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Repositories.IModels3DRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IModels3D;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class Models3DServiceImplements implements IModels3D {

    @Autowired
    private IModels3DRepository mR;

    @Override
    public void registrar(Models3D models3D) {
        mR.save(models3D);
    }

    @Override
    public Optional<Models3D> listarId(Integer id) {
        return mR.findById(id);
    }

    @Override
    public void actualizar(Models3D models3D) {
        mR.save(models3D);
    }

    @Override
    public List<Models3D> Listar() {
        return mR.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        mR.deleteById(id);
    }

    @Override
    public List<Models3D> buscarPorEstado(String state) {
        return mR.findByState(state);
    }

    @Override
    public List<Models3D> buscarPorFecha(LocalDate fecha) {
        return mR.buscarPorFecha(fecha);
    }

    @Override
    public List<Object[]> modelosConUbicacion() {
        return mR.modelosConUbicacion();
    }

    @Override
    public List<Object[]> inmueblesConModelo() {
        return mR.inmueblesConModelo();
    }

    @Override
    public List<Object[]> findEstatesWithoutModel() {
        return mR.findEstatesWithoutModel();
    }

    @Override
    public List<Object[]> findCitiesWithMostActiveModels() {
        return mR.findCitiesWithMostActiveModels();
    }

    @Override
    public List<Object[]> findStateRate() {
        return mR.findStateRate();
    }

    @Override
    public List<Object[]> findEstatesWithCriticalRiskPoints() {
        return mR.findEstatesWithCriticalRiskPoints();
    }
}
