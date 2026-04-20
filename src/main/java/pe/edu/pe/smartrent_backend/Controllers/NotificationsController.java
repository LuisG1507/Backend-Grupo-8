package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.NotificationsDTOS.NotificationsDTO;
import pe.edu.pe.smartrent_backend.Entities.Notifications;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.INotifications;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/Notifications")
public class NotificationsController {
    @Autowired
    private INotifications nS;

    @PostMapping
    public void registrar(@RequestBody NotificationsDTO dto){
        ModelMapper m= new ModelMapper();
        Notifications n= m.map(dto,Notifications.class);
        nS.Registrar(n);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable int id, @RequestBody NotificationsDTO dto) {
        ModelMapper m = new ModelMapper();
        Notifications n = m.map(dto, Notifications.class);
        n.setIdNotification(id);
        Notifications existente = nS.listId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se puede modificar.No existe un registro con el ID:" + id);
        }
        nS.Update(n);
        return ResponseEntity.ok("Registro con ID "+id+"Modificado exitosamente");
    }
    @GetMapping
    public List<NotificationsDTO>listar (){
        return nS.list().stream().map(x -> {ModelMapper m= new ModelMapper();
        return m.map(x, NotificationsDTO.class);
        }).collect(Collectors.toList());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar (@PathVariable("id") Integer id){
        Notifications n= nS.listId(id);
        if (n== null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un registro con el ID:"+id) ;
        }
        nS.Delete(id);
        return ResponseEntity.ok("Registro con ID"+id+"Elimando correctamente");
    }


}
