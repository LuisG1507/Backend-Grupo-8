package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDTO;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDTOudl;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDecisionDTO1;
import pe.edu.pe.smartrent_backend.DTOS.roleDTOS.RoleDecisionDTO2;
import pe.edu.pe.smartrent_backend.Entities.Role;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRole;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.ArrayList;
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
    public ResponseEntity<String> registrar(@RequestBody RoleDTO dto) {
        User u = uS.listId(dto.getIdUser()); // ← Busca el User real de la BD
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con ID: " + dto.getIdUser());
        }
        Role r = new Role();
        r.setRol(dto.getRol());
        r.setUser(u); // ← Ahora sí es un objeto gestionado por JPA ✅
        rS.Register(r);
        return ResponseEntity.ok("Rol registrado correctamente.");
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



//             ⡏⢱ ⣏⡉ ⡎⠑ ⡇ ⢎⡑ ⡇ ⡎⢱ ⡷⣸ ⣏⡉ ⢎⡑
//             ⠧⠜ ⠧⠤ ⠣⠔ ⠇ ⠢⠜ ⠇ ⠣⠜ ⠇⠹ ⠧⠤ ⠢⠜

    //Distribución de roles en la plataforma con porcentaje
    @GetMapping("/decision-01")
    public List<RoleDecisionDTO1> reporteDecision1() {
        List<Object[]> resultados = rS.RDecision1();
        List<RoleDecisionDTO1> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RoleDecisionDTO1 dto = new RoleDecisionDTO1();
            dto.setRol((String) row[0]);
            dto.setTotal(((Number) row[1]).intValue());
            dto.setPercentage(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return lista;
    }


    //Usuarios con más de un rol asignado
    @GetMapping("/decision-02")
    public List<RoleDecisionDTO2> reporteDecision2() {
        List<Object[]> resultados = rS.RDecision2();
        List<RoleDecisionDTO2> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            RoleDecisionDTO2 dto = new RoleDecisionDTO2();
            dto.setName((String) row[0]);
            dto.setLast_name((String) row[1]);
            dto.setCantidad_roles(((Number) row[2]).intValue());
            lista.add(dto);
        }
        return lista;
    }



}
