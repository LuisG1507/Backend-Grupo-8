package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Users")
public class
UserController {

    @Autowired
    private IUser uS;


    //Registrar
    @PostMapping("/Registrar")
    public void registrar(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        User p = m.map(dto, User.class);
        uS.Register(p);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();

        User existente = uS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }

        User p = m.map(dto, User.class);
        p.setIdUser(id);


        if (p.getRoles() != null && !p.getRoles().isEmpty()) {
            p.getRoles().forEach(role -> role.setUser(p));
        } else {

            p.setRoles(existente.getRoles());
        }

        uS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    //Listar
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserSinContraseniaDTO> listar() {
        return uS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }

    //Eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        User p = uS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        uS.Delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }


    //Listar por DNI
    @GetMapping("/findByDni/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        User p = uS.BuscarPorDNI(id);
        if (p == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        UserSinContraseniaDTO dto = m.map(p, UserSinContraseniaDTO.class);
        return ResponseEntity.ok(dto);
    }


    // Usuarios no verificados con antecedentes registrados
    @GetMapping("/unverified-with-backgrounds")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> unverifiedWithBackgrounds() {
        List<Object[]> resultados = uS.findUnverifiedUsersWithBackgrounds();

        List<UserUnverifiedWithBackgroundDTO> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            UserUnverifiedWithBackgroundDTO dto = new UserUnverifiedWithBackgroundDTO();

            dto.setName((String) row[0]);
            dto.setLastName((String) row[1]);
            dto.setTotalBackgrounds(((Number) row[2]).intValue());
            lista.add(dto);
        }

        return ResponseEntity.ok(lista);
    }

}
