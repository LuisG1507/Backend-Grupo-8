package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UsersIncidentsRankingDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UsersSinContraseniaDTO;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private IUser uS;


    //Registrar
    @PostMapping
    public void registrar(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        Users p = m.map(dto, Users.class);
        uS.Register(p);
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        Users p = m.map(dto, Users.class);
        p.setIdUser(id);


        Users existente = uS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }


        uS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    //Listar
    @GetMapping
    public List<UserDTO> listar() {
        return uS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserDTO.class);
        }).collect(Collectors.toList());
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
        Users p = uS.listId(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        uS.Delete(id);
        return ResponseEntity.ok("Registro con ID " + id + " eliminado correctamente.");
    }



    //Listar por DNI
    @GetMapping("/findByDni/{id}")
    public ResponseEntity<?> listarId(@PathVariable("id") Integer id) {
        Users p = uS.BuscarPorDNI(id);
        if (p == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        ModelMapper m = new ModelMapper();
        UsersSinContraseniaDTO dto = m.map(p, UsersSinContraseniaDTO.class);
        return ResponseEntity.ok(dto);
    }


    //Listar
    @GetMapping("/findByStatus")
    public List<UsersSinContraseniaDTO> fyndByStatus() {
        return uS.fyndByStatus().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UsersSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }


    //Listar por fechas
    @GetMapping("/findByCreatedDate/{f1}/{f2}")
    public List<UsersSinContraseniaDTO> fyndByCreatedDate(@PathVariable("f1") LocalDate f1,
                                                          @PathVariable("f2") LocalDate f2) {
        return uS.userByRangeDate(f1,f2).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UsersSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/RankingIncidents")
    public List<UsersIncidentsRankingDTO> RankingIncidents() {
        List<Object[]> resultados = uS.RankingUsuariosIncidencias();
        List<UsersIncidentsRankingDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            UsersIncidentsRankingDTO dto = new UsersIncidentsRankingDTO();
            dto.setNombre(((String) row[0]));
            dto.setCantidad(((Number) row[1]).intValue());
            lista.add(dto);
        }
        return lista;
    }


}
