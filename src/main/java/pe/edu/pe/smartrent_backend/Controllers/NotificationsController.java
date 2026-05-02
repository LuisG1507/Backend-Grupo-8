package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsTypeDTO;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Notifications")
public class NotificationsController {
    @Autowired
    private IUser uS;
    @Autowired
    private INotifications nS;

    @PostMapping("/registrar")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NotificationsCompleteDTO> registrar(@RequestBody NotificationsCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Notifications n = m.map(dto, Notifications.class);
        Notifications cur = nS.Registrar(n);
        NotificationsCompleteDTO responseDTO = m.map(cur, NotificationsCompleteDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/listar")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NotificationsDTO>>listar(){
        ModelMapper m= new ModelMapper();
        List<NotificationsDTO>lista=nS.list().stream().map(y ->m.map(y, NotificationsDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar (@PathVariable int id){
        Optional<Notifications>notifications= nS.listId(id);
        if (notifications.isPresent()){
           nS.Delete(id);
            return ResponseEntity.ok("Notificación eliminada correctamente") ;
        }
        else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificación no encontrada");
        }
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody NotificationsCompleteDTO dto) {
        // 1. Buscar la notificación existente
        Optional<Notifications> existente = nS.listId(dto.getIdNotification());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notificación no encontrada");
        }
        // 2. Validación de campos
        if (dto.getMessage() == null || dto.getMessage().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El contenido del mensaje no puede estar vacío");
        }
        Notifications n = existente.get();
        // 3. Actualizar datos básicos
        n.setTitle(dto.getTitle());
        n.setMessage(dto.getMessage());
        n.setType(dto.getType());
        n.setRead(dto.getRead());
        n.setCreatedDate(dto.getCreatedDate());
        // 4. Resolver la relación con User
        // Usamos el ID que viene en el DTO para obtener el objeto User completo
        User usuario = uS.listId(dto.getIdUser());
        n.setUser(usuario);
        // 5. Guardar cambios
        nS.Update(n);
        return ResponseEntity.ok("Notificación actualizada correctamente");
    }

    @GetMapping("/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Notifications> notification = nS.listId(id);

        if (notification.isPresent()) {
            NotificationsCompleteDTO dto = m.map(notification.get(), NotificationsCompleteDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notificación no encontrada");
        }
    }

    //QuerySimple
    @GetMapping("/no-leidas")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NotificationsDTO>> listarNoLeidas() {
        ModelMapper m = new ModelMapper();
        List<NotificationsDTO> lista = nS.buscarNoLeidos().stream()
                .map(y -> m.map(y, NotificationsDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    //QueryToma
    @GetMapping("/reporte-tipos")
    @PreAuthorize("hasAnyAuthority('ADMIN')") // Ajusta según tus permisos
    public List<NotificationsTypeDTO> reporteTipos() {
        return nS.getCountByType();
    }


}
