package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import java.util.List;
import java.util.Optional;

import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractEstateRotationDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractExpiringDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractLessorContractRateDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractLessorIncomeDTO;
import pe.edu.pe.smartrent_backend.Entities.Contract;

import java.util.List;
import java.util.Optional;

public interface IContractService {
    public List<Contract> list();
    public Contract insert(Contract c);
    public Optional<Contract> listId(int id);
    public void update(Contract c);
    public void delete(int id);
    List<Object[]> findLessorsAboveAverageIncome();
    List<Object[]> findContractRatePerLessor();
    List<Object[]> findEstatesWithHighestRotation();
    List<Object[]> findContractsExpiringSoon();
}
