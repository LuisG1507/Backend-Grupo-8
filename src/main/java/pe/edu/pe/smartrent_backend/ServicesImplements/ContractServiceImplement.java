package pe.edu.upc.api9233.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.api9233.entities.Contract;
import pe.edu.upc.api9233.repositories.IContractRepository;
import pe.edu.upc.api9233.servicesinterfaces.IContractService;

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
}