package pe.edu.pe.smartrent_backend.ServicesImplements;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.Repositories.IRiskReportRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskReport;

import java.util.List;

@Service
public class RiskReportServicesImplements implements IRiskReport {

    @Autowired
    private IRiskReportRepository rR;


    @Override
    public void Register(RiskReport r) {
        rR.save(r);
    }

    @Override
    public void Update(RiskReport r) {
        rR.save(r);
    }

    @Override
    public RiskReport listId(Integer id) {
        return rR.findById(id).orElse(null);
    }

    @Override
    public List<RiskReport> list() {
        return rR.findAll();
    }

    @Override
    public void Delete(Integer id) {
        rR.deleteById(id);
    }
}
