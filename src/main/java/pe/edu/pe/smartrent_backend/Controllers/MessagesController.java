package pe.edu.pe.smartrent_backend.Controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.messagesDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Messages;
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
    private IUser uS;
    @Autowired
    private IConversationService cS;
    @Autowired
    private IMessages mS;

    @PostMapping("/registrar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR', 'ARRENDATARIO')")
    public void registrar(@RequestBody MessagesCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Messages msg = m.map(dto, Messages.class);
        mS.Registrar(msg);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@PathVariable int id, @RequestBody MessagesCompleteDTO dto) {
        ModelMapper m = new ModelMapper();
        Messages msg = m.map(dto, Messages.class);
        msg.setIdMessage(id);

        Messages existente = mS.listId(id).orElse(null);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se puede modificar. No existe un registro con el ID: " + id);
        }

        mS.Update(msg);
        return ResponseEntity.ok("Registro con ID " + id + " modificado correctamente.");
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MessagesDTOInfinite>>listar(){
        ModelMapper m= new ModelMapper();
        List<MessagesDTOInfinite>lista=mS.list().stream().map(y ->m.map(y, MessagesDTOInfinite.class)).collect(Collectors.toList());
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
        Optional<Messages> message = mS.listId(id);

        if (message.isPresent()) {
            Messages m = message.get();

            MessagesDTOInfinite dto = new MessagesDTOInfinite();
            dto.setContent(m.getContent());
            dto.setStatus(m.getStatus());
            dto.setDateSent(m.getDateSent());

            MessagesDTOInfinite.UserBasicDTO userDTO = new MessagesDTOInfinite.UserBasicDTO();
            userDTO.setIdUser(m.getUser().getIdUser());
            userDTO.setName(m.getUser().getName());
            userDTO.setLastName(m.getUser().getLastName());
            userDTO.setUsername(m.getUser().getUsername());
            userDTO.setProfilePhoto(m.getUser().getProfilePhoto());
            userDTO.setPhoneNumber(m.getUser().getPhoneNumber());
            dto.setUser(userDTO);

            MessagesDTOInfinite.ConversationBasicDTO convDTO = new MessagesDTOInfinite.ConversationBasicDTO();
            convDTO.setIdConversation(m.getConversation().getIdConversation());
            dto.setConversation(convDTO);

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mensaje no encontrado");
        }
    }
}
