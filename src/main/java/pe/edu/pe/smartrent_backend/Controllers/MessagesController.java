package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.*;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS.NotificationsDTO;
import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IMessages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Messages")
public class MessagesController {
    @Autowired
    private IMessages mS;

    @PostMapping("/registrar")
    public ResponseEntity<MessagesCompleteDTO> registrar(@RequestBody MessagesCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Messages n = m.map(dto, Messages.class);
        Messages cur = mS.Registrar(n);
        MessagesCompleteDTO responseDTO = m.map(cur, MessagesCompleteDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody MessagesCompleteDTO dto) {
        Optional<Messages> existente = mS.listId(dto.getIdMessage());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mensaje no encontrado");
        }
        // Validación de campos (siguiendo el ejemplo de Project de la profe)
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("El contenido del mensaje no puede estar vacío");
        }
        Messages m = existente.get();
        m.setContent(dto.getContent());
        m.setStatus(dto.getStatus());
        m.setDateSent(dto.getDateSent());
        m.setConversation(dto.getConversation());
        m.setUser(dto.getUser());
        mS.Update(m);
        return ResponseEntity.ok("Mensaje actualizado correctamente");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MessagesDTO>>listar(){
        ModelMapper m= new ModelMapper();
        List<MessagesDTO>lista=mS.list().stream().map(y ->m.map(y, MessagesDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Messages> message = mS.listId(id);
        if (message.isPresent()) {
            mS.Delete(id);
            return ResponseEntity.ok("Mensaje eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mensaje no encontrado");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Messages> project = mS.listId(id);

        if (project.isPresent()) {
            MessagesDTO dto = m.map(project.get(), MessagesDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mensaje no encontrado");
        }
    }

    //QuerySimple
    @GetMapping("/buscar-por-estado")
    public ResponseEntity<List<MessagesDTO>> buscarPorEstado(@RequestParam String status) {
        ModelMapper m = new ModelMapper();
        List<MessagesDTO> lista = mS.findByStatus(status).stream()
                .map(y -> m.map(y, MessagesDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    //QueryTomaDecision
    @GetMapping("/urgentes-usuario")
    public ResponseEntity<List<MessagesUserQueryDTO>> listarUrgentes() {
        ModelMapper m = new ModelMapper();
        List<MessagesUserQueryDTO> lista = mS.findUrgentMessagesWithUserJPQL().stream()
                .map(y -> {
                    MessagesUserQueryDTO dto = m.map(y, MessagesUserQueryDTO.class);
                    dto.setUserName(y.getUser().getName()); // Mapeo manual del nombre del usuario
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // Usuarios con más mensajes urgentes (mayor riesgo operativo)
    @GetMapping("/urgent-users")
    public ResponseEntity<?> urgentUsers() {
        List<Object[]> resultados = mS.findUsersWithMostUrgentMessages();
        List<MessagesUrgentUserDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            MessagesUrgentUserDTO dto = new MessagesUrgentUserDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setUrgentMessages(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Distribución de mensajes por status con porcentaje
    @GetMapping("/status-distribution")
    public ResponseEntity<?> statusDistribution() {
        List<Object[]> resultados = mS.findMessageDistributionByStatus();
        List<MessagesStatusDistributionDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            MessagesStatusDistributionDTO dto = new MessagesStatusDistributionDTO();
            dto.setStatus(row[0].toString());
            dto.setTotal(((Number) row[1]).longValue());
            dto.setPercentage(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Conversaciones con mayor concentración de mensajes urgentes
    @GetMapping("/urgent-conversations")
    public ResponseEntity<?> urgentConversations() {
        List<Object[]> resultados = mS.findConversationsWithMostUrgentMessages();
        List<MessagesUrgentConversationDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            MessagesUrgentConversationDTO dto = new MessagesUrgentConversationDTO();
            dto.setIdConversation(((Number) row[0]).intValue());
            dto.setEstateTitle(row[1].toString());
            dto.setUrgentMessages(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    // Usuarios sin ningún mensaje enviado (posiblemente inactivos)
    @GetMapping("/inactive-users")
    public ResponseEntity<?> inactiveUsers() {
        List<Object[]> resultados = mS.findUsersWithNoMessages();
        List<MessagesInactiveUserDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            MessagesInactiveUserDTO dto = new MessagesInactiveUserDTO();
            dto.setIdUser(((Number) row[0]).intValue());
            dto.setName(row[1].toString());
            dto.setLastName(row[2].toString());
            dto.setCreatedDate((LocalDate) row[3]);
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }


}
