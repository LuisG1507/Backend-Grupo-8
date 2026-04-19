package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.EstateDTOS.EstateCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.EstateDTOS.EstateDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Repositories.IEstateRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Estate")
public class EstateController {

    @Autowired
    private IEstate eI;

    @PostMapping
    private void registrar(@RequestBody EstateDTO eD){
        ModelMapper m = new ModelMapper();
        Estate e = m.map(eD,Estate.class);
        eI.Register(e);
    }

    @GetMapping
    public ResponseEntity<?> listarTodo(){
        ModelMapper m = new ModelMapper();
        List<EstateDTO> list=eI.listar().stream().map(y->m.map(y,EstateDTO.class))
                .collect(Collectors.toList());

        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay valores en esta tabla");
        }else {
            return ResponseEntity.ok(list);
        }
    }

    @PutMapping("/actualizar")
    private ResponseEntity<String> actualizar(@RequestBody EstateCompleteDTO eC){
        Optional<Estate> exist = eI.listarId(eC.getIdEstate());
        if(exist.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El inmueble no fue encontrado");
        }
        Estate e = exist.get();

        e.setTitle(eC.getTitle());
        e.setDescription(eC.getDescription());
        e.setAdress(eC.getAdress());
        e.setDistrict(eC.getDistrict());
        e.setCity(eC.getCity());
        e.setMonthlyPrice(eC.getMonthlyPrice());
        e.setType(eC.getType());
        e.setState(eC.getState());
        e.setRooms(eC.getRooms());
        e.setBathrooms(eC.getBathrooms());
        e.setAreaM2(eC.getAreaM2());
        e.setCreationDate(eC.getCreationDate());
        e.setUsers(eC.getUsers());

        eI.Actualizar(e);

        return ResponseEntity.ok("Se ha actualizado de forma correcta");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id){
        Optional<Estate> vE = eI.listarId(id);
        if(vE.isPresent()){
            eI.eliminar(id);
            return ResponseEntity.ok("El valor ha sido eliminado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado el valor ingresado");
        }
    }

}
