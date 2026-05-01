package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserIncidentsRankingDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserSinContraseniaDTO;
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
    @PostMapping
    public void registrar(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        User p = m.map(dto, User.class);
        uS.Register(p);
    }

    //Modificar
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        User p = m.map(dto, User.class);
        p.setIdUser(id);


        User existente = uS.listId(id);
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


    //Listar
    @GetMapping("/findByStatus")
    public List<UserSinContraseniaDTO> fyndByStatus() {
        return uS.fyndByStatus().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }


    //Listar por fechas
    @GetMapping("/findByCreatedDate/{f1}/{f2}")
    public List<UserSinContraseniaDTO> fyndByCreatedDate(@PathVariable("f1") LocalDate f1,
                                                         @PathVariable("f2") LocalDate f2) {
        return uS.userByRangeDate(f1,f2).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/RankingIncidents")
    public List<UserIncidentsRankingDTO> RankingIncidents() {
        List<Object[]> resultados = uS.RankingUsuariosIncidencias();
        List<UserIncidentsRankingDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            UserIncidentsRankingDTO dto = new UserIncidentsRankingDTO();
            dto.setNombre(((String) row[0]));
            dto.setCantidad(((Number) row[1]).intValue());
            lista.add(dto);
        }
        return lista;
    }


}
