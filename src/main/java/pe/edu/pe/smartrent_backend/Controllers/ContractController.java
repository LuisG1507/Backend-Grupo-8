package pe.edu.pe.smartrent_backend.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractDTO;
import pe.edu.pe.smartrent_backend.Entities.Contract;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.Repositories.IEstateRepository;
import pe.edu.pe.smartrent_backend.Repositories.IUserRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IContractService;

import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.EstateWithoutActiveContractDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.LessorIncomeDTO;

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
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Contract> contractOpt = cS.listId(id);

        if (contractOpt.isPresent()) {
            cS.delete(id);
            return ResponseEntity.ok("Contract deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contract not found");
        }
    }

    @GetMapping("/reporte-ingresos-arrendador")
    public ResponseEntity<?> reporteIngresosArrendador() {
        List<Object[]> lista = cS.getIncomeByLessorNative();

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay contratos activos para generar el reporte");
        }

        List<LessorIncomeDTO> respuesta = new ArrayList<>();
        for (Object[] fila : lista) {
            LessorIncomeDTO dto = new LessorIncomeDTO();
            dto.setIdLessor(((Number) fila[0]).intValue());
            dto.setLessorName((String) fila[1]);
            dto.setContractCount(((Number) fila[2]).longValue());
            dto.setTotalMonthlyIncome(((Number) fila[3]).doubleValue());
            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/reporte-inmuebles-sin-contrato-activo")
    public ResponseEntity<?> reporteInmueblesSinContratoActivo() {
        List<Object[]> lista = cS.getEstatesWithoutActiveContractNative();

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay inmuebles sin contrato activo");
        }

        List<EstateWithoutActiveContractDTO> respuesta = new ArrayList<>();
        for (Object[] fila : lista) {
            EstateWithoutActiveContractDTO dto = new EstateWithoutActiveContractDTO();
            dto.setIdEstate(((Number) fila[0]).intValue());
            dto.setEstateTitle((String) fila[1]);
            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }

}
