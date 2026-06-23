package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.EstateCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS.*;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.RiskReportIdDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskPointsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RiskPoints")
public class RiskPointsController {

    @Autowired
    private IRiskPointsService rP;

    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<String> registrar(@RequestBody RiskPointsDTO rD) {
        ModelMapper m = new ModelMapper();
        RiskPoints r = m.map(rD, RiskPoints.class);

        Models3D mod = new Models3D();
        mod.setIdModels3D(rD.getIdModel3D());
        r.setModels3D(mod);

        rP.insert(r);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listarTodo() {
        ModelMapper m = new ModelMapper();
        List<RiskPointsCompleteDTO> list = rP.list().stream().map(y -> {
            RiskPointsCompleteDTO dto = m.map(y, RiskPointsCompleteDTO.class);
            dto.setId(y.getIdRiskPoints());
            dto.setIdModel3D(y.getModels3D().getIdModels3D());
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //listar por id
    @GetMapping("/listId/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        RiskPoints riskR = rP.listId(id);

        if (riskR != null) {
            RiskPointsIdDTO dto = m.map(riskR, RiskPointsIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Puntos de riesgo no encontrado");
        }
    }

    @PutMapping("/actualizar/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @RequestBody RiskPointsIdDTO dto) {
        RiskPoints exist = rP.listId(id);

        if (exist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El punto de riesgo no fue encontrado");
        }

        exist.setDescription(dto.getDescription());
        exist.setCordX(dto.getCordX());
        exist.setCordY(dto.getCordY());
        exist.setCordZ(dto.getCordZ());
        exist.setSeverity(dto.getSeverity());
        if (dto.getIdModel3D() != null) {
            Models3D model = new Models3D();
            model.setIdModels3D(dto.getIdModel3D().getIdModels3D());
            exist.setModels3D(model);
        }
        rP.update(exist);

        return ResponseEntity.ok("El punto de riesgo se ha actualizado correctamente");
    }


    @DeleteMapping("/eliminar/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        RiskPoints exist = rP.listId(id);

        if (exist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El punto de riesgo no fue encontrado");
        }

        rP.delete(id);
        return ResponseEntity.ok("El punto de riesgo ha sido eliminado correctamente");
    }
}