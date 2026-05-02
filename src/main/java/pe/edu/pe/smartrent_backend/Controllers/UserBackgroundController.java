package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.UsersBackground;
import pe.edu.pe.smartrent_backend.Repositories.IUserBackgroundRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUserBackground;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/UsersBackground")
public class UserBackgroundController {

    @Autowired
    private IUserBackground ubS;


    //Registrar
    @PostMapping
    public void registrar(@RequestBody UserBackgroundDTO dto) {
        ModelMapper m = new ModelMapper();
        UsersBackground p = m.map(dto, UsersBackground.class);
        ubS.Register(p);
    }


    //Listar
    @GetMapping
    public List<UserBackgroundGETDTO> listar() {
        return ubS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserBackgroundGETDTO.class);
        }).collect(Collectors.toList());
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserBackgroundDTO dto) {
        ModelMapper m = new ModelMapper();
        UsersBackground p = m.map(dto, UsersBackground.class);
        p.setIdBackground(id);


        UsersBackground existente = ubS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }


        ubS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }


    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        UsersBackground p = ubS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ubS.Delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }


    @GetMapping("/frequency-type")
    public List<UserBackgroundTypeFrequencyDTO> frequencyList() {
        return ubS.findMostFrequentTypes();
    }

    @GetMapping("/high-risk")
    public List<UserBackgroundAverageDTO> userBackgroundAverage() {
        return ubS.findHighRiskUsers();
    }

    @GetMapping("/sources-reports")
    public List<UserBackgroundSourceDTO> userBackgroundSource() {
        return ubS.findMostReportingSources();
    }

    @GetMapping("/monthly-trend")
    public List<UserBackgroundMonthlyDTO> monthlyList() {
        return ubS.findMonthlyTrend();
    }



}
