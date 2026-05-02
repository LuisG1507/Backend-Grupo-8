package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesDTO;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.MessagesDateActivityDTO;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IConversationService;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IMessages;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Messages")
public class MessagesController {

    @Autowired
    private IUser uS; // Agrega el servicio de usuarios
    @Autowired
    private IConversationService cS;
    @Autowired
    private IMessages mS;

    @PostMapping("/registrar")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessagesCompleteDTO> registrar(@RequestBody MessagesCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Messages n = m.map(dto, Messages.class);
        Messages cur = mS.Registrar(n);
        MessagesCompleteDTO responseDTO = m.map(cur, MessagesCompleteDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody MessagesCompleteDTO dto) {
        Optional<Messages> existente = mS.listId(dto.getIdMessage());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mensaje no encontrado");
        }
        // Validación de contenido
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("El contenido no puede estar vacío");
        }
        Messages m = existente.get();
        m.setContent(dto.getContent());
        m.setStatus(dto.getStatus());
        m.setDateSent(dto.getDateSent());
        // USANDO LOS SERVICIOS INYECTADOS
        // Buscamos al usuario y lo asignamos (asumiendo que listId devuelve la entidad)
        User usuario = uS.listId(dto.getIdUser());
        m.setUser(usuario);

        // Buscamos la conversación y la asignamos
        Conversation conv = cS.listId(dto.getIdConversation());
        m.setConversation(conv);
        mS.Update(m);
        return ResponseEntity.ok("Mensaje actualizado correctamente");
    }

    @GetMapping("/listar")
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MessagesDTO>>listar(){
        ModelMapper m= new ModelMapper();
        List<MessagesDTO>lista=mS.list().stream().map(y ->m.map(y, MessagesDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
    @DeleteMapping("/{id}")
        @PreAuthorize("hasAuthority('ADMIN')")
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
        @PreAuthorize("hasAuthority('ADMIN')")
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
        @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MessagesDTO>> buscarPorEstado(@RequestParam String status) {
        ModelMapper m = new ModelMapper();
        List<MessagesDTO> lista = mS.findByStatus(status).stream()
                .map(y -> m.map(y, MessagesDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    //QueryTomaDecision
    @GetMapping("/reporteActividadFechas")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MessagesDateActivityDTO>> reporteActividadFechas() {
        return ResponseEntity.ok(mS.findMessagesActivityByDate());
    }


}
