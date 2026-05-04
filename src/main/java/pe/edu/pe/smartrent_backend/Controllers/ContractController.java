package pe.edu.pe.smartrent_backend.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Contract;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.Repositories.IEstateRepository;
import pe.edu.pe.smartrent_backend.Repositories.IUserRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IContractService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Contracts")
public class ContractController {

    @Autowired
    private IContractService cS;

    @Autowired
    private IEstateRepository eR;

    @Autowired
    private IUserRepository uR;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
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
            dto.setIdLessor(c.getLessor().getIdUser());
            dto.setIdLessee(c.getLessee().getIdUser());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(contracts);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<?> create(@Valid @RequestBody ContractDTO dto) {
        Optional<Estate> estateOpt = eR.findById(dto.getIdEstate());
        Optional<User> lessorOpt = uR.findById(dto.getIdLessor());
        Optional<User> lesseeOpt = uR.findById(dto.getIdLessee());

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
        response.setIdLessor(saved.getLessor().getIdUser());
        response.setIdLessee(saved.getLessee().getIdUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
        dto.setIdLessor(c.getLessor().getIdUser());
        dto.setIdLessee(c.getLessee().getIdUser());

        return ResponseEntity.ok(dto);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<?> update(@Valid @RequestBody ContractDTO dto) {
        Optional<Contract> existingOpt = cS.listId(dto.getIdContract());
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contract not found");
        }

        Optional<Estate> estateOpt = eR.findById(dto.getIdEstate());
        Optional<User> lessorOpt = uR.findById(dto.getIdLessor());
        Optional<User> lesseeOpt = uR.findById(dto.getIdLessee());

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Contract> contractOpt = cS.listId(id);

        if (contractOpt.isPresent()) {
            cS.delete(id);
            return ResponseEntity.ok("Contract deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contract not found");
        }
    }

    @GetMapping("/lessors-above-average")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> lessorsAboveAverage() {
        List<Object[]> resultados = cS.findLessorsAboveAverageIncome();
        List<ContractLessorIncomeDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ContractLessorIncomeDTO dto = new ContractLessorIncomeDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setTotalIncome(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }
    @GetMapping("/contract-rate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> contractRate() {
        List<Object[]> resultados = cS.findContractRatePerLessor();
        List<ContractLessorContractRateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ContractLessorContractRateDTO dto = new ContractLessorContractRateDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setActive(((Number) row[2]).longValue());
            dto.setInactive(((Number) row[3]).longValue());
            dto.setTotal(((Number) row[4]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estate-rotation")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> estateRotation() {
        List<Object[]> resultados = cS.findEstatesWithHighestRotation();
        List<ContractEstateRotationDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ContractEstateRotationDTO dto = new ContractEstateRotationDTO();
            dto.setTitle(row[0].toString());
            dto.setCity(row[1].toString());
            dto.setDistrict(row[2].toString());
            dto.setTotalContracts(((Number) row[3]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/expiring-soon")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> expiringSoon() {
        List<Object[]> resultados = cS.findContractsExpiringSoon();
        List<ContractExpiringDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ContractExpiringDTO dto = new ContractExpiringDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setEstateTitle(row[2].toString());
            dto.setEndDate((LocalDateTime) row[3]);
            dto.setDaysRemaining(((Number) row[4]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }
}
