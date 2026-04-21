package pe.edu.upc.api9233.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.api9233.dtos.ContractDTO;
import pe.edu.upc.api9233.entities.Contract;
import pe.edu.upc.api9233.entities.Estate;
import pe.edu.upc.api9233.entities.Users;
import pe.edu.upc.api9233.repositories.IEstateRepository;
import pe.edu.upc.api9233.repositories.IUsersRepository;
import pe.edu.upc.api9233.servicesinterfaces.IContractService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private IContractService cS;

    @Autowired
    private IEstateRepository eR;

    @Autowired
    private IUsersRepository uR;

    @GetMapping("/list")
    public ResponseEntity<List<ContractDTO>> list() {
        List<ContractDTO> contracts = cS.list().stream().map(c -> {
            ContractDTO dto = new ContractDTO();
            dto.setIdContract(c.getIdContract());
            dto.setStartDate(c.getStartDate());
            dto.setEndDate(c.getEndDate());
            dto.setMonthlyAmount(c.getMonthlyAmount());
            dto.setStatus(c.isStatus());
            dto.setCreatedAt(c.getCreatedAt());
            dto.setIdEstate(c.getEstate().getIdEstate());
            dto.setIdLessor(c.getLessor().getIdUsers());
            dto.setIdLessee(c.getLessee().getIdUsers());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(contracts);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ContractDTO dto) {
        Optional<Estate> estateOpt = eR.findById(dto.getIdEstate());
        Optional<Users> lessorOpt = uR.findById(dto.getIdLessor());
        Optional<Users> lesseeOpt = uR.findById(dto.getIdLessee());

        if (estateOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("The estate does not exist");
        }
        if (lessorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("The lessor does not exist");
        }
        if (lesseeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("The lessee does not exist");
        }

        Contract c = new Contract();
        c.setStartDate(dto.getStartDate());
        c.setEndDate(dto.getEndDate());
        c.setMonthlyAmount(dto.getMonthlyAmount());
        c.setStatus(dto.isStatus());
        c.setCreatedAt(dto.getCreatedAt());
        c.setEstate(estateOpt.get());
        c.setLessor(lessorOpt.get());
        c.setLessee(lesseeOpt.get());

        Contract saved = cS.insert(c);

        ContractDTO response = new ContractDTO();
        response.setIdContract(saved.getIdContract());
        response.setStartDate(saved.getStartDate());
        response.setEndDate(saved.getEndDate());
        response.setMonthlyAmount(saved.getMonthlyAmount());
        response.setStatus(saved.isStatus());
        response.setCreatedAt(saved.getCreatedAt());
        response.setIdEstate(saved.getEstate().getIdEstate());
        response.setIdLessor(saved.getLessor().getIdUsers());
        response.setIdLessee(saved.getLessee().getIdUsers());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Contract> contractOpt = cS.listId(id);

        if (contractOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contract not found");
        }

        Contract c = contractOpt.get();
        ContractDTO dto = new ContractDTO();
        dto.setIdContract(c.getIdContract());
        dto.setStartDate(c.getStartDate());
        dto.setEndDate(c.getEndDate());
        dto.setMonthlyAmount(c.getMonthlyAmount());
        dto.setStatus(c.isStatus());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setIdEstate(c.getEstate().getIdEstate());
        dto.setIdLessor(c.getLessor().getIdUsers());
        dto.setIdLessee(c.getLessee().getIdUsers());

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ContractDTO dto) {
        Optional<Contract> existingOpt = cS.listId(dto.getIdContract());
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contract not found");
        }

        Optional<Estate> estateOpt = eR.findById(dto.getIdEstate());
        Optional<Users> lessorOpt = uR.findById(dto.getIdLessor());
        Optional<Users> lesseeOpt = uR.findById(dto.getIdLessee());

        if (estateOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("The estate does not exist");
        }
        if (lessorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("The lessor does not exist");
        }
        if (lesseeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("The lessee does not exist");
        }

        Contract c = existingOpt.get();
        c.setStartDate(dto.getStartDate());
        c.setEndDate(dto.getEndDate());
        c.setMonthlyAmount(dto.getMonthlyAmount());
        c.setStatus(dto.isStatus());
        c.setCreatedAt(dto.getCreatedAt());
        c.setEstate(estateOpt.get());
        c.setLessor(lessorOpt.get());
        c.setLessee(lesseeOpt.get());

        cS.update(c);

        return ResponseEntity.ok("Contract updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Contract> contractOpt = cS.listId(id);

        if (contractOpt.isPresent()) {
            cS.delete(id);
            return ResponseEntity.ok("Contract deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contract not found");
        }
    }
}