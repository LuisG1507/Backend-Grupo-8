package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import java.util.List;
import java.util.Optional;
import pe.edu.pe.smartrent_backend.Entities.Contract;


public interface IContractService {
    public List<Contract> list();

    public Contract insert(Contract c);

    public Optional<Contract> listId(int id);

    public void update(Contract c);

    public void delete(int id);

    public List<Object[]> findLessorsAboveAverageIncome();

    public List<Object[]> findContractRatePerLessor();

    public List<Object[]> findEstatesWithHighestRotation();

    public List<Object[]> findContractsExpiringSoon();
}
