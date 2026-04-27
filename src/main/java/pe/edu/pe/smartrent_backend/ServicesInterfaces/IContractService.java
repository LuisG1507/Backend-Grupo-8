package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.EstateWithoutActiveContractDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.LessorIncomeDTO;
import pe.edu.pe.smartrent_backend.Entities.Contract;

import java.util.List;
import java.util.Optional;

public interface IContractService {
    public List<Contract> list();
    public Contract insert(Contract c);
    public Optional<Contract> listId(int id);
    public void update(Contract c);
    public void delete(int id);

    public List<LessorIncomeDTO> getIncomeByLessor();
    public List<EstateWithoutActiveContractDTO> getEstatesWithoutActiveContract();
}
