package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskReport;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.ArrayList;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<String> registrar(@RequestBody RiskReportDTO dto) {

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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody RiskReportDTO dto) {

        RiskReport existente = rS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listar() {
        List<RiskReportDTO> aux = rS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, RiskReportDTO.class);
        }).collect(Collectors.toList());

        if (aux.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay registros encontrados");

        }
        return ResponseEntity.ok(aux);
    }

    //Eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        RiskReport p = rS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        rS.Delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }
}
