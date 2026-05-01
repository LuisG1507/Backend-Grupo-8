package pe.edu.pe.smartrent_backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.conversationDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IConversationService;

import java.util.ArrayList;
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
        u1.setIdUser(cD.getIdUser1());
        c.setUser1(u1);

        Users u2 = new Users();
        u2.setIdUser(cD.getIdUser2());
        c.setUser2(u2);

        Estate e = new Estate();
        e.setIdEstate(cD.getIdEstate());
        c.setEstate(e);

        cI.insert(c);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
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
    public ResponseEntity<String> actualizar(@RequestBody ConversationCompleteDTO cC) {
        Conversation exist = cI.listId(cC.getId());
        if (exist == null || exist.getIdConversation() == null) {
            return new ResponseEntity<>("La conversación no fue encontrada", HttpStatus.NOT_FOUND);
        }

        Users u1 = new Users();
        u1.setIdUser(cC.getIdUser1());
        exist.setUser1(u1);

        Users u2 = new Users();
        u2.setIdUser(cC.getIdUser2());
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

    //Listas tipo Object[]
    @GetMapping("/reporte-popularidad")
    public List<EstateConversationCountDTO> ECD() {
        List<Object[]> resultados = cI.getConversationCountPerEstate();
        List<EstateConversationCountDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            EstateConversationCountDTO dto = new EstateConversationCountDTO();
            dto.setEstateTitle(((String) row[0]));
            dto.setConversationCount(((Number) row[1]).intValue());
            lista.add(dto);
        }
        return lista;
    }

    @GetMapping("/unconverted-interest")
    public ResponseEntity<?> unconvertedInterest() {
        List<Object[]> resultados = cI.findEstatesWithConversationsButNoContract();
        List<ConversationEstateInterestDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ConversationEstateInterestDTO dto = new ConversationEstateInterestDTO();
            dto.setTitle(row[0].toString());
            dto.setCity(row[1].toString());
            dto.setTotalConversations(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/most-active-initiators")
    public ResponseEntity<?> mostActiveInitiators() {
        List<Object[]> resultados = cI.findMostActiveInitiators();
        List<ConversationUserCountDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ConversationUserCountDTO dto = new ConversationUserCountDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setTotalConversations(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/no-conversations")
    public ResponseEntity<?> noConversations() {
        List<Object[]> resultados = cI.findEstatesWithNoConversations();
        List<ConversationNoEstateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ConversationNoEstateDTO dto = new ConversationNoEstateDTO();
            dto.setIdEstate(((Number) row[0]).intValue());
            dto.setTitle(row[1].toString());
            dto.setCity(row[2].toString());
            dto.setMonthlyPrice(((Number) row[3]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/average-by-city")
    public ResponseEntity<?> averageByCity() {
        List<Object[]> resultados = cI.findAverageConversationsPerEstateByCity();
        List<ConversationAverageCityDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ConversationAverageCityDTO dto = new ConversationAverageCityDTO();
            dto.setCity(row[0].toString());
            dto.setTotalConversations(((Number) row[1]).longValue());
            dto.setTotalEstates(((Number) row[2]).longValue());
            dto.setAverage(((Number) row[3]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }
}