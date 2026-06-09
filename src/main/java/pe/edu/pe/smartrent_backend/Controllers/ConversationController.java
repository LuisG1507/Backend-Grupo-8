package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.*;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.EstateIdDTO;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IConversationService;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Conversations")
public class ConversationController {

    @Autowired
    private IConversationService cI;

    @Autowired
    private IMessages mS;

    @PostMapping
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> registrar(@RequestBody ConversationDTO cD) {
        Conversation c = new Conversation();

        User u1 = new User();
        u1.setIdUser(cD.getIdUser1());
        c.setUser1(u1);

        User u2 = new User();
        u2.setIdUser(cD.getIdUser2());
        c.setUser2(u2);

        Estate e = new Estate();
        e.setIdEstate(cD.getIdEstate());
        c.setEstate(e);

        cI.insert(c);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listarTodo() {
        List<ConversationCompleteDTO> list = cI.list().stream().map(y -> {
            ConversationCompleteDTO dto = new ConversationCompleteDTO();
            dto.setId(y.getIdConversation());
            dto.setIdUser1(y.getUser1().getIdUser());
            dto.setIdUser2(y.getUser2().getIdUser());
            dto.setIdEstate(y.getEstate().getIdEstate());
            return dto;
        }).collect(Collectors.toList());

        if (list.isEmpty()) {
            return new ResponseEntity<>("No hay valores en esta tabla", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PutMapping("/actualizar")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@RequestBody ConversationCompleteDTO cC) {
        Conversation exist = cI.listId(cC.getId());
        if (exist == null || exist.getIdConversation() == null) {
            return new ResponseEntity<>("La conversación no fue encontrada", HttpStatus.NOT_FOUND);
        }

        User u1 = new User();
        u1.setIdUser(cC.getIdUser1());
        exist.setUser1(u1);

        User u2 = new User();
        u2.setIdUser(cC.getIdUser2());
        exist.setUser2(u2);

        Estate e = new Estate();
        e.setIdEstate(cC.getIdEstate());
        exist.setEstate(e);

        cI.update(exist);

        return new ResponseEntity<>("Se ha actualizado de forma correcta", HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        Conversation exist = cI.listId(id);

        if (exist == null || exist.getIdConversation() == null) {
            return new ResponseEntity<>("No se ha encontrado el valor ingresado", HttpStatus.NOT_FOUND);
        }
        mS.deleteByConversation(id);
        cI.delete(id);

        return new ResponseEntity<>("La conversación y sus mensajes han sido eliminados", HttpStatus.OK);
    }

    //listar por id
    @GetMapping("/listId/{id}")

    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Conversation conversation = cI.listId(id);

        if (conversation != null) {
            ConversationIdDTO dto = m.map(conversation, ConversationIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La conversacion no existe");
        }
    }
}