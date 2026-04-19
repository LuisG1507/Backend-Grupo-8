package pe.edu.pe.smartrent_backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.ConversationCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.ConversationDTO;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IConversationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Conversations")
public class ConversationController {

    @Autowired
    private IConversationService cI;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody ConversationDTO cD) {
        Conversation c = new Conversation();

        Users u1 = new Users();
        u1.setId(cD.getIdUser1());
        c.setUser1(u1);

        Users u2 = new Users();
        u2.setId(cD.getIdUser2());
        c.setUser2(u2);

        Estate e = new Estate();
        e.setIdEstate(cD.getIdEstate()); // Corregido
        c.setEstate(e);

        cI.insert(c);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listarTodo() {
        List<ConversationCompleteDTO> list = cI.list().stream().map(y -> {
            ConversationCompleteDTO dto = new ConversationCompleteDTO();
            dto.setId(y.getIdConversation()); // Corregido: getIdConversation
            dto.setIdUser1(y.getUser1().getId());
            dto.setIdUser2(y.getUser2().getId());
            dto.setIdEstate(y.getEstate().getIdEstate()); // Corregido
            return dto;
        }).collect(Collectors.toList());

        if (list.isEmpty()) {
            return new ResponseEntity<>("No hay valores en esta tabla", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody ConversationCompleteDTO cC) {
        Conversation exist = cI.listId(cC.getId());
        if (exist == null || exist.getIdConversation() == null) {
            return new ResponseEntity<>("La conversación no fue encontrada", HttpStatus.NOT_FOUND);
        }

        Users u1 = new Users();
        u1.setId(cC.getIdUser1());
        exist.setUser1(u1);

        Users u2 = new Users();
        u2.setId(cC.getIdUser2());
        exist.setUser2(u2);

        Estate e = new Estate();
        e.setIdEstate(cC.getIdEstate());
        exist.setEstate(e);

        cI.update(exist);

        return new ResponseEntity<>("Se ha actualizado de forma correcta", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        Conversation exist = cI.listId(id);
        if (exist != null && exist.getIdConversation() != null) {
            cI.delete(id);
            return new ResponseEntity<>("El valor ha sido eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se ha encontrado el valor ingresado", HttpStatus.NOT_FOUND);
        }
    }
}