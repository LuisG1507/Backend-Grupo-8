package pe.edu.pe.smartrent_backend.ServicesImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractEstateRotationDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractExpiringDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractLessorContractRateDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractLessorIncomeDTO;
import pe.edu.pe.smartrent_backend.Entities.Contract;
import pe.edu.pe.smartrent_backend.Repositories.IContractRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IContractService;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImplement implements IContractService {

    @Autowired
    private IContractRepository cR;

    @Override
    public List<Contract> list() {
        return cR.findAll();
    }

    @Override
    public Contract insert(Contract c) {
        return cR.save(c);
    }

    @Override
    public Optional<Contract> listId(int id) {
        return cR.findById(id);
    }

    @Override
    public void update(Contract c) {
        cR.save(c);
    }

    
    @Override
    public void delete(int id) {
        cR.deleteById(id);
    }

    @Override
    public List<Object[]> findLessorsAboveAverageIncome() {
        return cR.findLessorsAboveAverageIncome();
    }

    @Override
    public List<Object[]> findContractRatePerLessor() {
        return cR.findContractRatePerLessor();
    }

    @Override
    public List<Object[]> findEstatesWithHighestRotation() {
        return cR.findEstatesWithHighestRotation();
    }

    @Override
    public List<Object[]> findContractsExpiringSoon() {
        return cR.findContractsExpiringSoon();
    }
}