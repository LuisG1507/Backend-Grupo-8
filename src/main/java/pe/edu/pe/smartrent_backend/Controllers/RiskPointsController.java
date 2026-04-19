package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS.RiskPointsCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS.RiskPointsDTO;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskPointsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RiskPoints")
public class RiskPointsController {

    @Autowired
    private IRiskPointsService rP;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody RiskPointsDTO rD) {
        ModelMapper m = new ModelMapper();
        RiskPoints r = m.map(rD, RiskPoints.class);

        Models3D mod = new Models3D();
        mod.setIdModels3D(rD.getIdModel3D()); // Corregido: idModels3D
        r.setModels3D(mod); // Corregido: setModels3D (con 's')

        rP.insert(r);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listarTodo() {
        ModelMapper m = new ModelMapper();
        List<RiskPointsCompleteDTO> list = rP.list().stream().map(y -> {
            RiskPointsCompleteDTO dto = m.map(y, RiskPointsCompleteDTO.class);
            dto.setId(y.getIdRiskPoints()); // Corregido: getIdRiskPoints
            dto.setIdModel3D(y.getModels3D().getIdModels3D()); // Corregido
            return dto;
        }).collect(Collectors.toList());

        if (list.isEmpty()) {
            return new ResponseEntity<>("No hay valores en esta tabla", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody RiskPointsCompleteDTO rC) {
        RiskPoints exist = rP.listId(rC.getId());
        if (exist == null || exist.getIdRiskPoints() == null) {
            return new ResponseEntity<>("El punto de riesgo no fue encontrado", HttpStatus.NOT_FOUND);
        }

        exist.setDescription(rC.getDescription());
        exist.setCordX(rC.getCordX());
        exist.setCordY(rC.getCordY());
        exist.setCordZ(rC.getCordZ());
        exist.setSeverity(rC.getSeverity());

        Models3D mod = new Models3D();
        mod.setIdModels3D(rC.getIdModel3D());
        exist.setModels3D(mod);

        rP.update(exist);

        return new ResponseEntity<>("Se ha actualizado de forma correcta", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        RiskPoints exist = rP.listId(id);
        if (exist != null && exist.getIdRiskPoints() != null) {
            rP.delete(id);
            return new ResponseEntity<>("El valor ha sido eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se ha encontrado el valor ingresado", HttpStatus.NOT_FOUND);
        }
    }
}