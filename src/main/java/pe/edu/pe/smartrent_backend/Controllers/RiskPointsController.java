package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRiskPointsService;

import java.util.ArrayList;
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
        mod.setIdModels3D(rD.getIdModel3D());
        r.setModels3D(mod);

        rP.insert(r);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
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



//             ⡏⢱ ⣏⡉ ⡎⠑ ⡇ ⢎⡑ ⡇ ⡎⢱ ⡷⣸ ⣏⡉ ⢎⡑
//             ⠧⠜ ⠧⠤ ⠣⠔ ⠇ ⠢⠜ ⠇ ⠣⠜ ⠇⠹ ⠧⠤ ⠢⠜


    //Modelos 3D con mayor cantidad de puntos de riesgo registrados
    @GetMapping("/decision-01")
    public List<RiskPointsDecisionDTO1> reporteDecision1() {
        List<Object[]> resultados = rP.RPDecision1();
        List<RiskPointsDecisionDTO1> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskPointsDecisionDTO1 dto = new RiskPointsDecisionDTO1();
            dto.setId_modelo3d(((Number) row[0]).intValue());
            dto.setTitle((String) row[1]);
            dto.setCity((String) row[2]);
            dto.setTotal_puntos(((Number) row[3]).intValue());
            lista.add(dto);
        }
        return lista;
    }


    //Distribución de puntos de riesgo por severidad con porcentaje
    @GetMapping("/decision-02")
    public List<RiskPointsDecisionDTO2> reporteDecision2() {
        List<Object[]> resultados = rP.RPDecision2();
        List<RiskPointsDecisionDTO2> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskPointsDecisionDTO2 dto = new RiskPointsDecisionDTO2();
            dto.setSeverity((String) row[0]);
            dto.setTotal(((Number) row[1]).intValue());
            dto.setPorcentaje(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return lista;
    }


    // Inmuebles con más puntos de severidad crítica (prioridad de intervención)
    @GetMapping("/decision-03")
    public List<RiskPointsDecisionDTO3> reporteDecision3() {
        List<Object[]> resultados = rP.RPDecision3();
        List<RiskPointsDecisionDTO3> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskPointsDecisionDTO3 dto = new RiskPointsDecisionDTO3();
            dto.setTitle((String) row[0]);
            dto.setCity((String) row[1]);
            dto.setDistrict((String) row[2]);
            dto.setCritic_points(((Number) row[3]).intValue());
            lista.add(dto);
        }
        return lista;
    }

    // Modelos 3D sin ningún punto de riesgo registrado
    @GetMapping("/decision-04")
    public List<RiskPointsDecisionDTO4> reporteDecision4() {
        List<Object[]> resultados = rP.RPDecision4();
        List<RiskPointsDecisionDTO4> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RiskPointsDecisionDTO4 dto = new RiskPointsDecisionDTO4();
            dto.setId_modelo3d(((Number) row[0]).intValue());
            dto.setTitle((String) row[1]);
            dto.setCity((String) row[2]);
            dto.setCreate_date((java.time.LocalDate) row[3]);
            lista.add(dto);
        }
        return lista;
    }


}