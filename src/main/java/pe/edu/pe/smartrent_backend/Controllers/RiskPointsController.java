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
}