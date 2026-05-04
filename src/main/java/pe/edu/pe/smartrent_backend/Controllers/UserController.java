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
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
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
    public List<UserDTO> listar() {
        return uS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserDTO.class);
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


    //Listar
    @GetMapping("/findByStatus")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserSinContraseniaDTO> fyndByStatus() {
        return uS.fyndByStatus().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }


    //Listar por fechas
    @GetMapping("/findByCreatedDate/{f1}/{f2}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserSinContraseniaDTO> fyndByCreatedDate(@PathVariable("f1") LocalDate f1,
                                                         @PathVariable("f2") LocalDate f2) {
        return uS.userByRangeDate(f1,f2).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, UserSinContraseniaDTO.class);
        }).collect(Collectors.toList());
    }

    @GetMapping("/RankingIncidents")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
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

    // Crecimiento de usuarios registrados por mes
    @GetMapping("/monthly-growth")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserEnabledByRoleDTO> reporteUsuariosHabilitadosPorRol() {
        List<Object[]> resultados = uS.findEnabledUsersByRole();
        List<UserEnabledByRoleDTO> lista = new ArrayList<>();

        for (Object[] row : resultados) {
            UserEnabledByRoleDTO dto = new UserEnabledByRoleDTO();
            dto.setRole((String) row[0]);
            dto.setEnabled(((Number) row[1]).intValue());
            dto.setDisabled(((Number) row[2]).intValue());
            lista.add(dto);
        }
        return lista;
    }

}
