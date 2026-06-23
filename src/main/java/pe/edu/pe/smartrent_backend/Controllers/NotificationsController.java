package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Notifications")
public class NotificationsController {
    @Autowired
    private INotifications nS;

    @PostMapping("/Registrar")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NotificationsCompleteDTO> registrar(@RequestBody NotificationsCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Notifications n = m.map(dto, Notifications.class);
        Notifications cur = nS.Registrar(n);
        NotificationsCompleteDTO responseDTO = m.map(cur, NotificationsCompleteDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/list")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public ResponseEntity<List<NotificationDTOInfinite>> listar() {
        ModelMapper m = new ModelMapper();
        List<NotificationDTOInfinite> lista = nS.list().stream().map(y -> m.map(y, NotificationDTOInfinite.class)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Notifications> notifications = nS.listId(id);
        if (notifications.isPresent()) {
            nS.Delete(id);
            return ResponseEntity.ok("Notificación eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada");
        }
    }

    @PutMapping("/actualizar")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody NotificationsCompleteDTO dto) {
        Optional<Notifications> existente = nS.listId(dto.getIdNotification());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mensaje no encontrado");
        }

        if (dto.getMessage() == null || dto.getMessage().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El contenido del mensaje no puede estar vacío");
        }
        Notifications m = existente.get();
        m.setTitle(dto.getTitle());
        m.setMessage(dto.getMessage());
        m.setType(dto.getType());
        m.setRead(dto.getRead());
        m.setCreatedDate(dto.getCreatedDate());
        m.setUser(dto.getUser());
        nS.Update(m);
        return ResponseEntity.ok("Mensaje actualizado correctamente");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Optional<Notifications> notification = nS.listId(id);

        if (notification.isPresent()) {
            Notifications n = notification.get();

            NotificationDTOInfinite dto = new NotificationDTOInfinite();
            dto.setTitle(n.getTitle());
            dto.setMessage(n.getMessage());
            dto.setType(n.getType());
            dto.setRead(n.getRead());
            dto.setCreatedDate(n.getCreatedDate());

            NotificationDTOInfinite.UserBasicDTO userDTO = new NotificationDTOInfinite.UserBasicDTO();
            userDTO.setIdUser(n.getUser().getIdUser());
            userDTO.setName(n.getUser().getName());
            userDTO.setLastName(n.getUser().getLastName());
            userDTO.setUsername(n.getUser().getUsername());
            userDTO.setProfilePhoto(n.getUser().getProfilePhoto());
            userDTO.setPhoneNumber(n.getUser().getPhoneNumber());

            dto.setUser(userDTO);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notificación no encontrada");
        }
    }
}
