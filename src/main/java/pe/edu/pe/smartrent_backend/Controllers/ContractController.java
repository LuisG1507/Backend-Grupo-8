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
        String validationError = validarContrato(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato no encontrado");
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

        String validationError = validarContrato(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
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

    //Luciana

    @GetMapping("/revenue-by-district")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> revenueByDistrict() {
        List<Object[]> resultados = cS.findRevenueByDistrict();
        List<ContractRevenueDistrictDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ContractRevenueDistrictDTO dto = new ContractRevenueDistrictDTO();
            dto.setDistrict(row[0].toString());
            dto.setTotalRevenue(((Number) row[1]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/average-duration-lessor")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> averageDurationByLessor() {
        List<Object[]> resultados = cS.findAverageContractDurationByLessor();
        List<ContractAverageDurationDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ContractAverageDurationDTO dto = new ContractAverageDurationDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setAverageDays(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    private String validarContrato(ContractDTO dto) {
        if (dto.getStartDate() == null || dto.getEndDate() == null) {
            return "Las fechas de inicio y fin son obligatorias.";
        }
        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            return "La fecha de fin debe ser posterior a la fecha de inicio.";
        }
        if (dto.getCreatedAt() == null || dto.getCreatedAt().isAfter(LocalDateTime.now().plusMinutes(1))) {
            return "La fecha de creacion no puede estar en el futuro.";
        }
        if (dto.getMonthlyAmount() == null || dto.getMonthlyAmount() <= 0
                || dto.getMonthlyAmount() > 100000) {
            return "El monto mensual debe ser mayor que 0 y menor o igual a 100000.";
        }
        if (dto.getIdEstate() <= 0 || dto.getIdLessor() <= 0 || dto.getIdLessee() <= 0) {
            return "Seleccione un inmueble, arrendador y arrendatario validos.";
        }
        if (dto.getIdLessor() == dto.getIdLessee()) {
            return "El arrendador y el arrendatario deben ser usuarios diferentes.";
        }
        return null;
    }

}
