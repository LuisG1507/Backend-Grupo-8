package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDTO;
import pe.edu.pe.smartrent_backend.Entities.Role;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRole;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RolesController")

public class RoleController {

    @Autowired
    private IRole rS;

    @Autowired
    private IUser uS;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> registrar(@RequestBody RoleDTO dto) {
        String validationError = validarRol(dto);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        User u = uS.listId(dto.getIdUser());
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con ID: " + dto.getIdUser());
        }
        Role r = new Role();
        r.setRol(dto.getRol());
        r.setUser(u);
        rS.Register(r);
        return ResponseEntity.ok("Rol registrado correctamente.");
    }

    //Listar
    @GetMapping("/ListarRoles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RoleDTO> listar() {
        return rS.list().stream().map(x -> {
            RoleDTO dto = new RoleDTO();
            dto.setId(x.getId());
            dto.setRol(x.getRol());
            if (x.getUser() != null) {
                dto.setIdUser(x.getUser().getIdUser());
            }
            return dto;
        }).collect(Collectors.toList());
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody RoleDTO dto) {
        try {
            String validationError = validarRol(dto);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }

            if (dto.getRol() == null || dto.getRol().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("El campo 'rol' no puede estar vacío");
            }

            Role existente = rS.listId(id);
            if (existente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se puede modificar. No existe un registro con el ID: " + id);
            }

            User u = uS.listId(dto.getIdUser());
            if (u == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe un usuario con ID: " + dto.getIdUser());
            }

            Role p = new Role();
            p.setId(id);
            p.setRol(dto.getRol());
            p.setUser(u);

            rS.Update(p);
            return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body("El rol '" + dto.getRol() +
                            "' ya existe para este usuario");
        }
    }

    //Eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Role p = rS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        rS.Delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }


    //Listar por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Role p = rS.listId(id);
        if (p == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        RoleDTO dto = new RoleDTO();
        dto.setId(p.getId());
        dto.setRol(p.getRol());
        if (p.getUser() != null) {
            dto.setIdUser(p.getUser().getIdUser());
        }
        return ResponseEntity.ok(dto);
    }

    private String validarRol(RoleDTO dto) {
        if (dto.getRol() == null || (!dto.getRol().equals("ADMIN")
                && !dto.getRol().equals("ARRENDADOR")
                && !dto.getRol().equals("ARRENDATARIO"))) {
            return "Seleccione un rol valido.";
        }
        if (dto.getIdUser() == null || dto.getIdUser() <= 0) {
            return "Seleccione un usuario valido.";
        }
        return null;
    }

}
