package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import pe.edu.pe.smartrent_backend.Repositories.IRiskPointsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskPointsService;

import java.util.List;

@Service
public class RiskPointsServiceImplements implements IRiskPointsService {

    @Autowired
    private IRiskPointsRepository rP;

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


    //Toma decisiones

    @Override
    public List<Object[]> RPDecision1() {
        return rP.RPD1();
    }

    @Override
    public List<Object[]> RPDecision2() {
        return rP.RPD2();
    }

    @Override
    public List<Object[]> RPDecision3() {
        return rP.RPD3();
    }

    @Override
    public List<Object[]> RPDecision4() {
        return rP.RPD4();
    }


}