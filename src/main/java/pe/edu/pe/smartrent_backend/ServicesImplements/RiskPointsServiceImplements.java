package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import pe.edu.pe.smartrent_backend.Repositories.RiskPointsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskPointsService;

import java.util.List;

@Service
public class RiskPointsServiceImplements implements IRiskPointsService {

    @Autowired
    private RiskPointsRepository rP;

    @Override
    public void insert(RiskPoints riskPoint) {
        rP.save(riskPoint);
    }

    @Override
    public List<RiskPoints> list() {
        return rP.findAll();
    }

    @Override
    public void delete(Integer id) {
        rP.deleteById(id);
    }

    @Override
    public RiskPoints listId(Integer id) {
        return rP.findById(id).orElse(null);
    }

    @Override
    public void update(RiskPoints riskPoint) {
        rP.save(riskPoint);
    }
}