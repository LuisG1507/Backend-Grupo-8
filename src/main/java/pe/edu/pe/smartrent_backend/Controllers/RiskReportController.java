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

    //Listar
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RiskReportDTO> listar() {
        return rS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, RiskReportDTO.class);
        }).collect(Collectors.toList());
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



//             ⡏⢱ ⣏⡉ ⡎⠑ ⡇ ⢎⡑ ⡇ ⡎⢱ ⡷⣸ ⣏⡉ ⢎⡑
//             ⠧⠜ ⠧⠤ ⠣⠔ ⠇ ⠢⠜ ⠇ ⠣⠜ ⠇⠹ ⠧⠤ ⠢⠜


    //// Inmuebles con más reportes de riesgo
    @GetMapping("/Decision1")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
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

  //4. Inmuebles con nivel de riesgo ALTO que aún tienen contrato activo (situación crítica)
    @GetMapping("/decision-04")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public List<RiskReportDecisionDTO4> reporteDecision4() {
        List<Object[]> resultados = rS.RRDecision4();
        List<RiskReportDecisionDTO4> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskReportDecisionDTO4 dto = new RiskReportDecisionDTO4();
            dto.setTitle((String) row[0]);
            dto.setCity((String) row[1]);
            dto.setDistrict((String) row[2]);
            dto.setReportes_altos(((Number) row[3]).intValue());
            lista.add(dto);
        }
        return lista;
    }

}
