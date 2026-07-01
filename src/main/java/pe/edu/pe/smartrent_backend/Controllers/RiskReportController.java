package pe.edu.pe.smartrent_backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskReport;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RiskReport")
public class RiskReportController {

    @Autowired
    private IRiskReport rS;

    @Autowired
    private IUser uS;

    @Autowired
    private IEstate eS;

    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<String> registrar(@RequestBody RiskReportDTO dto) {
        String validationError = validarReporte(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        User u = uS.listId(dto.getIdUser());
        if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe usuario con ID: " + dto.getIdUser());

        Estate e = eS.listarId(dto.getIdEstate()).orElse(null);
        if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe propiedad con ID: " + dto.getIdEstate());

        RiskReport p = new RiskReport();
        p.setType(dto.getType());
        p.setCreationDate(dto.getCreationDate());
        p.setRiskLevel(dto.getRiskLevel());
        p.setDescription(dto.getDescription());
        p.setDetails(dto.getDetails());
        p.setUser(u);
        p.setEstate(e);

        rS.Register(p);
        return ResponseEntity.ok("Reporte de riesgo registrado correctamente.");
    }

    @PutMapping("/{id}")
   // @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody RiskReportDTO dto) {

        RiskReport existente = rS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }

        String validationError = validarReporte(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        User u = uS.listId(dto.getIdUser());
        if (u == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe usuario con ID: " + dto.getIdUser());

        Estate e = eS.listarId(dto.getIdEstate()).orElse(null);
        if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe propiedad con ID: " + dto.getIdEstate());

        existente.setType(dto.getType());
        existente.setCreationDate(dto.getCreationDate());
        existente.setRiskLevel(dto.getRiskLevel());
        existente.setDescription(dto.getDescription());
        existente.setDetails(dto.getDetails());
        existente.setUser(u);
        existente.setEstate(e);

        rS.Update(existente);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> listar() {
        List<RiskReportIdDTO> aux = rS.list().stream()
                .map(this::toRiskReportIdDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(aux);
    }

    // Lista solamente los reportes asociados a inmuebles del arrendador autenticado.
    @GetMapping("/my-reports")
    @PreAuthorize("hasAuthority('ARRENDADOR')")
    public ResponseEntity<?> listarMisReportes(Authentication authentication) {
        List<RiskReportIdDTO> reports = rS.listByUsername(authentication.getName()).stream()
                .map(this::toRiskReportIdDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reports);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        RiskReport p = rS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        rS.Delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }
  
  
   //listar por id
    @GetMapping("/listId/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        RiskReport riskR = rS.listId(id);

        if (riskR != null) {
            return ResponseEntity.ok(toRiskReportIdDTO(riskR));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Informe de riesgo no encontrado");
        }
    }

    private RiskReportIdDTO toRiskReportIdDTO(RiskReport riskReport) {
        RiskReportIdDTO dto = new RiskReportIdDTO();
        dto.setIdRiskReport(riskReport.getIdRiskReport());
        dto.setType(riskReport.getType());
        dto.setCreationDate(riskReport.getCreationDate());
        dto.setRiskLevel(riskReport.getRiskLevel());
        dto.setDescription(riskReport.getDescription());
        dto.setDetails(riskReport.getDetails());
        if (riskReport.getUser() != null) {
            dto.setIdUser(riskReport.getUser().getIdUser());
        }
        if (riskReport.getEstate() != null) {
            dto.setIdEstate(riskReport.getEstate().getIdEstate());
        }
        return dto;
    }

    private String validarReporte(RiskReportDTO dto) {
        if (dto.getType() == null || dto.getType().trim().length() < 3
                || dto.getType().trim().length() > 50) {
            return "El tipo debe contener entre 3 y 50 caracteres.";
        }
        if (dto.getCreationDate() == null || dto.getCreationDate().isAfter(java.time.LocalDate.now())) {
            return "La fecha del reporte no puede estar en el futuro.";
        }
        if (dto.getRiskLevel() == null || (!dto.getRiskLevel().equals("BAJO")
                && !dto.getRiskLevel().equals("MEDIO")
                && !dto.getRiskLevel().equals("ALTO"))) {
            return "Seleccione un nivel de riesgo valido.";
        }
        if (dto.getDescription() == null || dto.getDescription().trim().length() < 10
                || dto.getDescription().trim().length() > 500) {
            return "La descripcion debe contener entre 10 y 500 caracteres.";
        }
        if (dto.getDetails() == null || dto.getDetails().trim().length() < 5
                || dto.getDetails().trim().length() > 200) {
            return "Los detalles deben contener entre 5 y 200 caracteres.";
        }
        if (dto.getIdUser() == null || dto.getIdUser() <= 0
                || dto.getIdEstate() == null || dto.getIdEstate() <= 0) {
            return "Seleccione un usuario y un inmueble validos.";
        }
        return null;
    }
}
