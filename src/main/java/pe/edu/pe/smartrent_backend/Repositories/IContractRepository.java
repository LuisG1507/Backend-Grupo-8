package pe.edu.upc.api9233.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.api9233.entities.Contract;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Integer> {
}