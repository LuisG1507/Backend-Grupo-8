package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<?> registrar(@RequestBody EstateCreateDTO eD, Authentication authentication){
        String validationError = validarInmueble(
                eD.getTitle(), eD.getDescription(), eD.getAdress(), eD.getDistrict(),
                eD.getCity(), eD.getMonthlyPrice(), eD.getType(), eD.getState(),
                eD.getRooms(), eD.getBathrooms(), eD.getAreaM2(), eD.getCreationDate());
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }

        // El propietario se obtiene del usuario autenticado, no de un ID escrito en el formulario.
        User user = uS.findByUsername(authentication.getName());

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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> listarTodo(){
        ModelMapper m = new ModelMapper();
        List<EstateCompleteDTO> list = eI.listar().stream().map(y -> {
                    EstateCompleteDTO dto = m.map(y, EstateCompleteDTO.class);
                    if (y.getUser() != null) {
                        dto.setOwnerName(y.getUser().getName());
                        dto.setOwnerLastName(y.getUser().getLastName());
                        dto.setOwnerPhoneNumber(y.getUser().getPhoneNumber());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my-estates")
    @PreAuthorize("hasAuthority('ARRENDADOR')")
    public ResponseEntity<?> listarMisInmuebles(Authentication authentication) {
        ModelMapper m = new ModelMapper();
        List<EstateCompleteDTO> list = eI.listarPorUsername(authentication.getName()).stream()
                .map(y -> {
                    EstateCompleteDTO dto = m.map(y, EstateCompleteDTO.class);
                    if (y.getUser() != null) {
                        dto.setOwnerName(y.getUser().getName());
                        dto.setOwnerLastName(y.getUser().getLastName());
                        dto.setOwnerPhoneNumber(y.getUser().getPhoneNumber());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
    public ResponseEntity<String> actualizar(@RequestBody EstateCompleteDTO eC){
        Optional<Estate> exist = eI.listarId(eC.getIdEstate());
        if(exist.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El inmueble no fue encontrado");
        }

        String validationError = validarInmueble(
                eC.getTitle(), eC.getDescription(), eC.getAdress(), eC.getDistrict(),
                eC.getCity(), eC.getMonthlyPrice(), eC.getType(), eC.getState(),
                eC.getRooms(), eC.getBathrooms(), eC.getAreaM2(), eC.getCreationDate());
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDADOR')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
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

    private String validarInmueble(
            String title, String description, String adress, String district, String city,
            Double monthlyPrice, String type, Boolean state, Integer rooms,
            Integer bathrooms, Double areaM2, java.time.LocalDate creationDate) {
        if (title == null || title.trim().length() < 3 || title.trim().length() > 100) {
            return "El titulo debe contener entre 3 y 100 caracteres.";
        }
        if (description == null || description.trim().length() < 10 || description.trim().length() > 200) {
            return "La descripcion debe contener entre 10 y 200 caracteres.";
        }
        if (adress == null || adress.trim().length() < 5 || adress.trim().length() > 200) {
            return "La direccion debe contener entre 5 y 200 caracteres.";
        }
        if (district == null || district.trim().length() < 2 || district.trim().length() > 100) {
            return "El distrito debe contener entre 2 y 100 caracteres.";
        }
        if (city == null || city.trim().length() < 2 || city.trim().length() > 100) {
            return "La ciudad debe contener entre 2 y 100 caracteres.";
        }
        if (monthlyPrice == null || monthlyPrice <= 0 || monthlyPrice > 100000) {
            return "El precio mensual debe ser mayor que 0 y menor o igual a 100000.";
        }
        if (!"Casa".equals(type) && !"Departamento".equals(type)) {
            return "Seleccione Casa o Departamento como tipo de inmueble.";
        }
        if (state == null) {
            return "Seleccione el estado del inmueble.";
        }
        if (rooms == null || rooms < 1 || rooms > 20) {
            return "Las habitaciones deben estar entre 1 y 20.";
        }
        if (bathrooms == null || bathrooms < 1 || bathrooms > 20) {
            return "Los banos deben estar entre 1 y 20.";
        }
        if (areaM2 == null || areaM2 <= 0 || areaM2 > 10000) {
            return "El area debe ser mayor que 0 y menor o igual a 10000 m2.";
        }
        if (creationDate == null || creationDate.isAfter(java.time.LocalDate.now())) {
            return "La fecha de creacion no puede estar en el futuro.";
        }
        return null;
    }
}
