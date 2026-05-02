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

    @Override
    public List<UserBackgroundTypeFrequencyDTO> findMostFrequentTypes() {
        return ubR.userBackgroundType();
    }

    @Override
    public List<UserBackgroundAverageDTO> findHighRiskUsers() {
        return ubR.findHighRiskUsers();
    }

    @Override
    public List<UserBackgroundSourceDTO> findMostReportingSources() {
        return ubR.findMostReportingSources();
    }

    @Override
    public List<UserBackgroundMonthlyDTO> findMonthlyTrend() {
        return ubR.findMonthlyTrend();
    }
}
