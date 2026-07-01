package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserIdDTO;
import pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.Entities.UsersBackground;
import pe.edu.pe.smartrent_backend.Repositories.IUserBackgroundRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUserBackground;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/UsersBackground")
public class UserBackgroundController {

    @Autowired
    private IUserBackground ubS;

    @Autowired
    private IUser uS;


    //Registrar
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<String> registrar(@RequestBody UserBackgroundDTO dto) {
        String validationError = validarAntecedente(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        if (uS.listId(dto.getUser().getIdUser()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        ModelMapper m = new ModelMapper();
        UsersBackground p = m.map(dto, UsersBackground.class);
        ubS.Register(p);
        return ResponseEntity.ok("Antecedente registrado correctamente.");
    }

    //Listar
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public List<UserBackgroundGETDTO> listar() {
        return ubS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            UserBackgroundGETDTO dto = new UserBackgroundGETDTO();
            dto.setIdUserBackground(x.getIdBackground());
            if (x.getUser() != null) {
                dto.setIdUser(x.getUser().getIdUser());
            }
            dto.setType(x.getType());
            dto.setDescription(x.getDescription());
            dto.setSource(x.getSource());
            dto.setRegistrationDate(x.getRegistrationDate());

            return dto;
        }).collect(Collectors.toList());
    }
    //listar por id
    @GetMapping("/listId/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        UsersBackground user = ubS.listId(id);

        if (user != null) {
            UserBackgroundIdDTO dto = m.map(user, UserBackgroundIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Userbackground no encontrado");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserBackgroundDTO dto) {
        ModelMapper m = new ModelMapper();
        UsersBackground p = m.map(dto, UsersBackground.class);
        p.setIdBackground(id);


        UsersBackground existente = ubS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }

        String validationError = validarAntecedente(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        if (uS.listId(dto.getUser().getIdUser()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        ubS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }


    //Eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public List<UserBackgroundTypeFrequencyDTO> reporteDecision1() {
        List<Object[]> resultados = ubS.findMostFrequentTypes();
        List<UserBackgroundTypeFrequencyDTO> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            UserBackgroundTypeFrequencyDTO dto = new UserBackgroundTypeFrequencyDTO();
            dto.setType((String) row[0]);
            dto.setTotal(((Number) row[1]).intValue());
            dto.setPercentage(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return lista;
    }

    @GetMapping("/high-risk")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public List<UserBackgroundAverageDTO> userBackgroundAverage() {
        List<Object[]> resultados = ubS.findHighRiskUsers();
        List<UserBackgroundAverageDTO> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            UserBackgroundAverageDTO dto = new UserBackgroundAverageDTO();
            dto.setName((String) row[0]);
            dto.setLastName((String) row[1]);
            dto.setTotalBackground(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return lista;
    }

    private String validarAntecedente(UserBackgroundDTO dto) {
        if (dto.getType() == null || dto.getType().trim().length() < 3
                || dto.getType().trim().length() > 50) {
            return "El tipo debe contener entre 3 y 50 caracteres.";
        }
        if (dto.getDescription() == null || dto.getDescription().trim().length() < 10
                || dto.getDescription().trim().length() > 200) {
            return "La descripcion debe contener entre 10 y 200 caracteres.";
        }
        if (dto.getSource() == null || dto.getSource().trim().length() < 3
                || dto.getSource().trim().length() > 50) {
            return "La fuente debe contener entre 3 y 50 caracteres.";
        }
        if (dto.getRegistrationDate() == null
                || dto.getRegistrationDate().isAfter(java.time.LocalDate.now())) {
            return "La fecha de registro no puede estar en el futuro.";
        }
        if (dto.getUser() == null || dto.getUser().getIdUser() == null
                || dto.getUser().getIdUser() <= 0) {
            return "Seleccione un usuario valido.";
        }
        return null;
    }

}
