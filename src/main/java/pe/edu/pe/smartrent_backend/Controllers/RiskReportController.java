package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.RiskReportDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserDTO;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskReport;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RiskReport")
public class RiskReportController {

    @Autowired
    private IRiskReport rS;

    //Registrar
    @PostMapping
    public void registrar(@RequestBody RiskReportDTO dto) {
        ModelMapper m = new ModelMapper();
        RiskReport p = m.map(dto, RiskReport.class);
        rS.Register(p);
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody RiskReportDTO dto) {
        ModelMapper m = new ModelMapper();
        RiskReport p = m.map(dto, RiskReport.class);
        p.setIdRiskReport(id);


        RiskReport existente = rS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }


        rS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    //Listar
    @GetMapping
    public List<RiskReportDTO> listar() {
        return rS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, RiskReportDTO.class);
        }).collect(Collectors.toList());
    }

    //Eliminar
    @DeleteMapping("/{id}")
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
