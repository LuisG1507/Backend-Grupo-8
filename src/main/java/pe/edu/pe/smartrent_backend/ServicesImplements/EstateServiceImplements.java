package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.EstateDTOS.OwnerEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.EstateDTOS.UserEstateDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Repositories.IEstateRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;

import java.util.List;
import java.util.Optional;

@Service
public class EstateServiceImplements implements IEstate {

    @Autowired
    private IEstateRepository eR;

    @Override
    public void Register(Estate e) {
        eR.save(e);
    }

    @Override
    public Optional<Estate> listarId(Integer id) {
        return eR.findById(id);
    }

    @Override
    public void Actualizar(Estate estE) {
        eR.save(estE);
    }

    @Override
    public List<Estate> listar() {
        return eR.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        eR.deleteById(id);
    }

    @Override
    public List<Estate> filtrarInmueblesPorCiudadDistritoTipo(String city, String district, String type) {
        return eR.filtroCityDistrictType(city,district,type);
    }

    @Override
    public double amountTotal() {
        return eR.SumTotal();
    }

    @Override
    public List<OwnerEstateDTO> listUsersEstate() {
        return eR.listUserEstate();
    }

    @Override
    public List<UserEstateDTO> listINNERJOIN(String d) {
        return eR.listUserEstateDistrict(d);
    }
}
