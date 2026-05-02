package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundAverageDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundMonthlyDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundSourceDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.UserBackgroundTypeFrequencyDTO;
import pe.edu.pe.smartrent_backend.Entities.UsersBackground;
import pe.edu.pe.smartrent_backend.Repositories.IUserBackgroundRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUserBackground;

import java.util.List;

@Service
public class UserBackgroundServicesImplements implements IUserBackground {

    @Autowired
    private IUserBackgroundRepository ubR;


    @Override
    public List<UsersBackground> list() {
        return ubR.findAll();
    }

    @Override
    public void Register(UsersBackground ub) {
        ubR.save(ub);
    }

    @Override
    public void Update(UsersBackground ub) {
        ubR.save(ub);
    }

    @Override
    public UsersBackground listId(Integer id) {
        return  ubR.findById(id).orElse(null);
    }

    @Override
    public void Delete(Integer id) {
        ubR.deleteById(id);
    }

    //UserBackgroundTypeFrequencyDTO
    @Override
    public List<Object[]> findMostFrequentTypes() {
        return ubR.userBackgroundType();
    }

    //UserBackgroundAverageDTO
    @Override
    public List<Object[]> findHighRiskUsers() {
        return ubR.findHighRiskUsers();
    }

    //UserBackgroundSourceDTO
    @Override
    public List<Object[]> findMostReportingSources() {
        return ubR.findMostReportingSources();
    }

    //UserBackgroundMonthlyDTO
    @Override
    public List<Object[]> findMonthlyTrend() {
        return ubR.findMonthlyTrend();
    }
}
