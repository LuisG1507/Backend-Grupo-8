package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.MessagesDTOS.MessagesDTO;
import pe.edu.pe.smartrent_backend.Entities.Messages;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IMessages;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Messages")
public class MessagesController {
    @Autowired
    private IMessages mS;

    @PostMapping
    public void registrar(@RequestBody MessagesDTO dto){
        ModelMapper m= new ModelMapper();
        Messages n= m.map(dto,Messages.class);
        mS.Registrar(n);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody MessagesDTO dto) {
        ModelMapper m = new ModelMapper();
        Messages n = m.map(dto, Messages.class);
        n.setIdMessage(id);
        Messages existente = mS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede modificar.No existe un registro con el ID:" + id);
        }
        mS.Update(n);
        return ResponseEntity.ok("Registro con ID "+id+"Modificado exitosamente");
    }
    @GetMapping
    public List<MessagesDTO> listar (){
        return (List<MessagesDTO>) mS.list().stream().map(x ->
        {
            ModelMapper m = new ModelMapper();
            return m.map(x, MessagesDTO.class);
        }).collect(Collectors.toList());

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable("id") Integer id){
        Messages n= mS.listId(id);
        if (n== null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un registro con el ID:"+id) ;
        }
        mS.Delete(id);
        return ResponseEntity.ok("Registro con ID"+id+"Elimando correctamente");
    }

}
