package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.RiskReportDTO;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.RiskReportDecisionDTO1;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.RiskReportDecisionDTO2;
import pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS.RiskReportDecisionDTO3;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskReport;

import java.util.ArrayList;
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



//             ⡏⢱ ⣏⡉ ⡎⠑ ⡇ ⢎⡑ ⡇ ⡎⢱ ⡷⣸ ⣏⡉ ⢎⡑
//             ⠧⠜ ⠧⠤ ⠣⠔ ⠇ ⠢⠜ ⠇ ⠣⠜ ⠇⠹ ⠧⠤ ⠢⠜


    //// Inmuebles con más reportes de riesgo
    @GetMapping("/Decision1")
    public List<RiskReportDecisionDTO1> D1() {
        List<Object[]> resultados = rS.RRDecision1();
        List<RiskReportDecisionDTO1> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            RiskReportDecisionDTO1 dto = new RiskReportDecisionDTO1();
            dto.setTitle(((String) row[0]));
            dto.setCity(((String) row[1]));
            dto.setTotal_reportes(((Number) row[2]).intValue());
            lista.add(dto);
        }
        return lista;
    }


    // Distribución de reportes por nivel de riesgo con porcentaje
    @GetMapping("/Decision2")
    public List<RiskReportDecisionDTO2> obtenerReporteRiesgos() {
        List<Object[]> resultados = rS.RRDecision2();
        List<RiskReportDecisionDTO2> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskReportDecisionDTO2 dto = new RiskReportDecisionDTO2();

            dto.setRisk_level((String) row[0]);

            dto.setTotal(((Number) row[1]).intValue());

            dto.setPorcentaje(((Number) row[2]).doubleValue());

            lista.add(dto);
        }
        return lista;
    }

    // Usuarios que más reportes han generado

    @GetMapping("/Decision3")
    public List<RiskReportDecisionDTO3> reporteDecision3() {
        List<Object[]> resultados = rS.RRDecision3();
        List<RiskReportDecisionDTO3> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskReportDecisionDTO3 dto = new RiskReportDecisionDTO3();
            dto.setName((String) row[0]);
            dto.setLast_name((String) row[1]);
            dto.setTotal_reports(((Number) row[2]).intValue());
            lista.add(dto);
        }
        return lista;
    }

}
