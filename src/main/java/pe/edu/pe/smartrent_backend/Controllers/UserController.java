package pe.edu.pe.smartrent_backend.Controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Role;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Users")
public class UserController {

    @Autowired
    private IUser uS;


    //Registrar
    @PostMapping("/Registrar")
    public ResponseEntity<String> registrar(@RequestBody UserDTO dto) {
        String validationError = validarUsuario(dto, true);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        // En el registro publico nunca se permite crear un usuario ADMIN.
        if (!"ARRENDADOR".equals(dto.getRole())
                && !"ARRENDATARIO".equals(dto.getRole())) {
            return ResponseEntity.badRequest()
                    .body("Seleccione el rol ARRENDADOR o ARRENDATARIO.");
        }

        if (uS.findByUsername(dto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya se encuentra registrado.");
        }
        if (uS.BuscarPorDNI(dto.getDni()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El DNI ya se encuentra registrado.");
        }

        ModelMapper m = new ModelMapper();
        User p = m.map(dto, User.class);

        // Estos valores los controla el sistema, no el formulario.
        p.setCreatedDate(LocalDate.now());
        p.setUpdateDate(LocalDate.now());
        p.setStatusVerification(false);
        p.setEnabled(true);

        Role role = new Role();
        role.setRol(dto.getRole());
        role.setUser(p);
        p.setRoles(List.of(role));

        uS.Register(p);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado correctamente.");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody UserDTO dto) {
        ModelMapper m = new ModelMapper();

        User existente = uS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }

        String validationError = validarUsuario(dto, false);
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        User sameUsername = uS.findByUsername(dto.getUsername());
        if (sameUsername != null && !sameUsername.getIdUser().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya se encuentra registrado.");
        }
        User sameDni = uS.BuscarPorDNI(dto.getDni());
        if (sameDni != null && !sameDni.getIdUser().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El DNI ya se encuentra registrado.");
        }

        User p = m.map(dto, User.class);
        p.setIdUser(id);
        p.setCreatedDate(existente.getCreatedDate());
        p.setUpdateDate(LocalDate.now());

        if (p.getPassword() == null || p.getPassword().trim().isEmpty()) {
            p.setPassword(existente.getPassword());
        }

        if (p.getRoles() != null && !p.getRoles().isEmpty()) {
            p.getRoles().forEach(role -> role.setUser(p));
        } else {

            p.setRoles(existente.getRoles());
        }

        uS.Update(p);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    //Listar
    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserSinContraseniaDTO> listar() {
        return uS.list().stream()
                .map(this::toUserWithoutPasswordDTO)
                .collect(Collectors.toList());
    }

    //Eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    public ResponseEntity<?> listarDni(@PathVariable("id") Integer id) {
        User p = uS.BuscarPorDNI(id);
        if (p == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No existe un registro con el ID: " + id);
        }
        return ResponseEntity.ok(toUserWithoutPasswordDTO(p));
    }

    //listar por id
    @GetMapping("/listarporId/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        User user = uS.listId(id);

        if (user != null) {
            UserIdDTO dto = m.map(user, UserIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Proyecto no encontrado");
        }
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

    private UserSinContraseniaDTO toUserWithoutPasswordDTO(User user) {
        ModelMapper mapper = new ModelMapper();
        UserSinContraseniaDTO dto = mapper.map(user, UserSinContraseniaDTO.class);

        String roles = user.getRoles() == null
                ? ""
                : user.getRoles().stream()
                .filter(role -> role.getRol() != null)
                .map(role -> role.getRol())
                .collect(Collectors.joining(", "));

        dto.setRole(roles);
        return dto;
    }

    private String validarUsuario(UserDTO dto, boolean passwordRequired) {
        if (dto.getName() == null || !dto.getName().trim().matches("[\\p{L} ]{2,50}")) {
            return "El nombre debe contener entre 2 y 50 letras.";
        }
        if (dto.getLastName() == null || !dto.getLastName().trim().matches("[\\p{L} ]{2,50}")) {
            return "El apellido debe contener entre 2 y 50 letras.";
        }
        if (dto.getDni() == null || dto.getDni() < 10000000 || dto.getDni() > 99999999) {
            return "El DNI debe contener exactamente 8 digitos.";
        }
        if (dto.getUsername() == null || !dto.getUsername().matches("[A-Za-z0-9_]{4,20}")) {
            return "El usuario debe tener entre 4 y 20 caracteres y no usar espacios.";
        }
        if (passwordRequired && (dto.getPassword() == null || dto.getPassword().length() < 6)) {
            return "La contrasena debe tener al menos 6 caracteres.";
        }
        if (!passwordRequired && dto.getPassword() != null && !dto.getPassword().isBlank()
                && dto.getPassword().length() < 6) {
            return "La nueva contrasena debe tener al menos 6 caracteres.";
        }
        if (dto.getProfilePhoto() == null || dto.getProfilePhoto().length() > 1000
                || (!dto.getProfilePhoto().startsWith("http://")
                && !dto.getProfilePhoto().startsWith("https://"))) {
            return "Ingrese una URL valida para la foto de perfil.";
        }
        if (dto.getPhoneNumber() == null || !dto.getPhoneNumber().matches("9\\d{8}")) {
            return "El telefono debe comenzar con 9 y contener 9 digitos.";
        }
        return null;
    }

}
