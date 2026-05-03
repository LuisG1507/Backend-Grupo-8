package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/web")
    public ResponseEntity<NotificationsCompleteDTO> registrar(@RequestBody NotificationsCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Notifications n = m.map(dto, Notifications.class);
        Notifications cur = nS.Registrar(n);
        NotificationsCompleteDTO responseDTO = m.map(cur, NotificationsCompleteDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<NotificationDTOInfinite>> listar() {
        ModelMapper m = new ModelMapper();
        List<NotificationDTOInfinite> lista = nS.list().stream().map(y -> m.map(y, NotificationDTOInfinite.class)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
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
    public ResponseEntity<String> actualizar(@RequestBody NotificationsCompleteDTO dto) {
        Optional<Notifications> existente = nS.listId(dto.getIdNotification());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mensaje no encontrado");
        }
        // Validación de campos (siguiendo el ejemplo de Project de la profe)
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
    public ResponseEntity<?> buscarPorIde(@PathVariable int id) {
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

    //QuerySimple
    @GetMapping("/no-leidas")
    public ResponseEntity<List<NotificationDTOInfinite>> listarNoLeidas() {
        ModelMapper m = new ModelMapper();
        List<NotificationDTOInfinite> lista = nS.buscarNoLeidos().stream()
                .map(y -> m.map(y, NotificationDTOInfinite.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    //QueryToma


    //Tasa de lectura por tipo de notificación
    @GetMapping("/read-rate")
    public ResponseEntity<?> readRate() {
        List<Object[]> resultados = nS.findReadRateByType();
        List<NotificationReadRateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            NotificationReadRateDTO dto = new NotificationReadRateDTO();
            dto.setType(row[0].toString());
            dto.setTotal(((Number) row[1]).longValue());
            dto.setRead(((Number) row[2]).longValue());
            dto.setReadRate(((Number) row[3]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Usuarios con más notificaciones no leídas (requieren atención inmediata)
    @GetMapping("/unread-users")
    public ResponseEntity<?> unreadUsers() {
        List<Object[]> resultados = nS.findUsersWithMostUnreadNotifications();
        List<NotificationUnreadUserDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            NotificationUnreadUserDTO dto = new NotificationUnreadUserDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setPending(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Tipos de notificación más generados en el último mes
    @GetMapping("/monthly-types")
    public ResponseEntity<?> monthlyTypes() {
        List<Object[]> resultados = nS.findMostGeneratedTypesLastMonth();
        List<NotificationTypeMonthlyDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            NotificationTypeMonthlyDTO dto = new NotificationTypeMonthlyDTO();
            dto.setType(row[0].toString());
            dto.setTotal(((Number) row[1]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Días con mayor generación de alertas de seguridad
    @GetMapping("/security-alerts")
    public ResponseEntity<?> securityAlerts() {
        List<Object[]> resultados = nS.findDaysWithMostSecurityAlerts();
        List<NotificationSecurityAlertDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            NotificationSecurityAlertDTO dto = new NotificationSecurityAlertDTO();
            dto.setCreatedDate((LocalDate) row[0]); // directo, ya es LocalDate
            dto.setTotalAlerts(((Number) row[1]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

}
