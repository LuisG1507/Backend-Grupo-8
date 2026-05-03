package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public void registrar(@RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();
        User p = m.map(dto, User.class);
        uS.Register(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();

        User existente = uS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }

        User p = m.map(dto, User.class);
        p.setIdUser(id);

        // 3. RECONECTAR LOS ROLES (Paso crítico)
        // Si el DTO trajo roles, debemos asegurar que cada rol reconozca a 'p' como su dueño
        if (p.getRoles() != null && !p.getRoles().isEmpty()) {
            p.getRoles().forEach(role -> role.setUser(p));
        } else {
            // Si el DTO no trajo roles, le devolvemos los que ya tenía en la BD
            // para que Hibernate no intente borrarlos o ponerles null
            p.setRoles(existente.getRoles());
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

    // Usuarios verificados vs no verificados con porcentaje
    @GetMapping("/verification-stats")
    public ResponseEntity<?> verificationStats() {
        List<Object[]> resultados = uS.findVerificationStats();
        List<UserVerificationStatsDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            UserVerificationStatsDTO dto = new UserVerificationStatsDTO();
            dto.setVerified(((Number) row[0]).longValue());
            dto.setNotVerified(((Number) row[1]).longValue());
            dto.setVerifiedPercentage(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok( lista);
    }

    // Usuarios no verificados con antecedentes registrados (alto riesgo)
    @GetMapping("/unverified-backgrounds")
    public List<UserUnverifiedWithBackgroundDTO> reporteUsuariosNoVerificadosConAntecedentes() {
        // 1. Llamar al Service que ejecuta la query nativa
        List<Object[]> resultados = uS.findUnverifiedUsersWithBackgrounds();
        List<UserUnverifiedWithBackgroundDTO> lista = new ArrayList<>();

        // 2. Recorrer los resultados y mapear manualmente al DTO
        for (Object[] row : resultados) {
            UserUnverifiedWithBackgroundDTO dto = new UserUnverifiedWithBackgroundDTO();

            dto.setName((String) row[0]);     // u.name
            dto.setLastName((String) row[1]); // u.last_name

            // 3. Casstear el conteo (COUNT) de forma segura usando Number
            if (row[2] != null) {
                dto.setTotalBackgrounds(((Number) row[2]).intValue());
            }

            lista.add(dto);
        }
        return lista;
    }

    // Crecimiento de usuarios registrados por mes
    @GetMapping("/monthly-growth")
    public ResponseEntity<?> monthlyGrowth() {
        List<Object[]> resultados = uS.findMonthlyGrowth();
        List<UserMonthlyGrowthDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            UserMonthlyGrowthDTO dto = new UserMonthlyGrowthDTO();
            dto.setMonth(row[0].toString());
            dto.setNewUsers(((Number) row[1]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/enabled-by-role")
    public List<UserEnabledByRoleDTO> reporteUsuariosHabilitadosPorRol() {
        List<Object[]> resultados = uS.findEnabledUsersByRole();
        List<UserEnabledByRoleDTO> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            UserEnabledByRoleDTO dto = new UserEnabledByRoleDTO();

            dto.setRole((String) row[0]); // r.rol

            if (row[1] != null) {
                dto.setEnabled(((Number) row[1]).intValue());
            }
            if (row[2] != null) {
                dto.setDisabled(((Number) row[2]).intValue());
            }

            lista.add(dto);
        }
        return lista;
    }

}
