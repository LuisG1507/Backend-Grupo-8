package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Estate")
public class EstateController {

    @Autowired
    private IEstate eI;

    @Autowired
    private IUser uS;

    @PostMapping
    // @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<?> registrar(@RequestBody EstateCreateDTO eD){
        User user = uS.listId(eD.getIdUser());

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        Estate e = new Estate();
        e.setTitle(eD.getTitle());
        e.setDescription(eD.getDescription());
        e.setAdress(eD.getAdress());
        e.setDistrict(eD.getDistrict());
        e.setCity(eD.getCity());
        e.setMonthlyPrice(eD.getMonthlyPrice());
        e.setType(eD.getType());
        e.setState(eD.getState());
        e.setRooms(eD.getRooms());
        e.setBathrooms(eD.getBathrooms());
        e.setAreaM2(eD.getAreaM2());
        e.setCreationDate(eD.getCreationDate());
        e.setUser(user);

        eI.Register(e);
        return ResponseEntity.ok("Estate registrado correctamente");
    }

    @GetMapping("/listAll")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> listarTodo(){
        ModelMapper m = new ModelMapper();
        List<EstateCompleteDTO> list = eI.listar().stream().map(y->m.map(y,EstateCompleteDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/actualizar")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
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
        if (eC.getIdUser() != null) {
            User u = new User();
            u.setIdUser(eC.getIdUser().getIdUser());
            e.setUser(u);
        }

        eI.Actualizar(e);

        return ResponseEntity.ok("Se ha actualizado de forma correcta");
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<String> eliminar(@PathVariable Integer id){
        Optional<Estate> vE = eI.listarId(id);
        if(vE.isPresent()){
            try {
                eI.eliminar(id);
                return ResponseEntity.ok("El valor ha sido eliminado");
            } catch (DataIntegrityViolationException exception) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("No se puede eliminar el inmueble porque tiene registros relacionados");
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado el valor ingresado");
        }
    }

    //listar por id
    @GetMapping("/listId/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Estate> estate = eI.listarId(id);

        if (estate.isPresent()) {
            EstateIdDTO dto = m.map(estate.get(), EstateIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("EL inmueble no existe");
        }
    }

    @GetMapping("/filtro/{ciudad}/{distrito}/{tipo}")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> filtroEstate(
            @PathVariable("ciudad") String ciudad,
            @PathVariable("distrito") String distrito,
            @PathVariable("tipo") String tipo){

        List<Estate> estates = eI.filtrarInmueblesPorCiudadDistritoTipo(ciudad, distrito, tipo);

        if(estates.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay inmuebles con ese filtro");
        }

        List<EstateFilterDTO> list = estates.stream().map(e -> {
            EstateFilterDTO dto = new EstateFilterDTO();
            dto.setIdEstate(e.getIdEstate());
            dto.setTitle(e.getTitle());
            dto.setDescription(e.getDescription());
            dto.setAdress(e.getAdress());
            dto.setDistrict(e.getDistrict());
            dto.setCity(e.getCity());
            dto.setMonthlyPrice(e.getMonthlyPrice());
            dto.setType(e.getType());
            dto.setState(e.getState());
            dto.setRooms(e.getRooms());
            dto.setBathrooms(e.getBathrooms());
            dto.setAreaM2(e.getAreaM2());
            dto.setCreationDate(e.getCreationDate());

            EstateFilterDTO.UserBasicDTO userDTO = new EstateFilterDTO.UserBasicDTO();
            userDTO.setIdUser(e.getUser().getIdUser());
            userDTO.setName(e.getUser().getName());
            userDTO.setLastName(e.getUser().getLastName());
            userDTO.setUsername(e.getUser().getUsername());
            dto.setUser(userDTO);

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }



    @GetMapping("/owners-estates")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<List<OwnerEstateDTO>> listUsersEst() {
        List<Object[]> results = eI.listUsersEstate();
        List<OwnerEstateDTO> lista = new ArrayList<>();

        for (Object[] row : results) {
            OwnerEstateDTO dto = new OwnerEstateDTO();
            dto.setName((String) row[0]);
            dto.setLastname((String) row[1]);
            dto.setRooms(((Number) row[2]).intValue());
            dto.setMonthlyPrice(((Number) row[3]).doubleValue());
            lista.add(dto);
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/user-estate/{district}")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<List<EstateUsersDTO>> ListaUser(@PathVariable String district) {
        List<Object[]> results = eI.EstateDistrict(district);
        List<EstateUsersDTO> lista = new ArrayList<>();

        for (Object[] row : results) {
            EstateUsersDTO dto = new EstateUsersDTO();
            dto.setName((String) row[0]);
            dto.setLastname((String) row[1]);
            dto.setCity((String) row[2]);
            dto.setDistrict((String) row[3]);
            dto.setMonthlyPrice(((Number) row[4]).doubleValue());
            lista.add(dto);
        }

        return ResponseEntity.ok(lista);
    }

    //Listas tipo Object[]
    @GetMapping("/AlquilerEncimaDelPromedio")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public List<AboveAverageRentsDTO> AVG() {
        List<Object[]> resultados = eI.AboveAverageRents();
        List<AboveAverageRentsDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            AboveAverageRentsDTO dto = new AboveAverageRentsDTO();
            dto.setTitle(((String) row[0]));
            dto.setDistrict(((String) row[1]));
            dto.setMontlhy_price(((Double) row[2]).doubleValue());
            dto.setRooms(((Integer) row[3]));
            lista.add(dto);
        }
        return lista;
    }



    @GetMapping("/best-price-per-room")
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> bestPricePerRoom() {
        List<Object[]> resultados = eI.findBestPricePerRoom();
        List<EstatePricePerRoomDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            EstatePricePerRoomDTO dto = new EstatePricePerRoomDTO();
            dto.setTitle(row[0].toString());
            dto.setCity(row[1].toString());
            dto.setRooms(((Number) row[2]).intValue());
            dto.setMonthlyPrice(((Number) row[3]).doubleValue());
            dto.setPricePerRoom(((Number) row[4]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

 

    @GetMapping("/price-range-distribution")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> priceRangeDistribution() {
        List<Object[]> resultados = eI.findDistributionByTypeAndPriceRange();
        List<EstatePriceRangeDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            EstatePriceRangeDTO dto = new EstatePriceRangeDTO();
            dto.setType(row[0].toString());
            dto.setLowRange(((Number) row[1]).longValue());
            dto.setMidRange(((Number) row[2]).longValue());
            dto.setHighRange(((Number) row[3]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }
}
