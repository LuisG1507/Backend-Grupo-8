package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDTO;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDTOudl;
import pe.edu.pe.smartrent_backend.Entities.Role;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRole;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RolesController")
public class RoleController {

    @Autowired
    private IRole rS;

    //Registrar
    @PostMapping
    public void registrar(@RequestBody RoleDTOudl dto) {
        ModelMapper m = new ModelMapper();
        Role p = m.map(dto, Role.class);
        rS.Register(p);
    }

    //Listar
    @GetMapping("/ListarRoles")
    public List<RoleDTO> listar() {
        return rS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, RoleDTO.class);
        }).collect(Collectors.toList());
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody RoleDTOudl dto) {
        ModelMapper m = new ModelMapper();
        Role p = m.map(dto, Role.class);
        p.setId(id);


        Role existente = rS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }


        rS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    //Eliminar
    @DeleteMapping("/{id}")
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
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Role p = rS.listId(id);
        if (p == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        RoleDTO dto = m.map(p,RoleDTO .class);
        return ResponseEntity.ok(dto);
    }


}
