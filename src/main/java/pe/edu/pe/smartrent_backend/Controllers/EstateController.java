package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;

import java.util.ArrayList;
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

    @GetMapping("/listAll")
    public ResponseEntity<?> listarTodo(){
        ModelMapper m = new ModelMapper();
        List<EstateDTO> list = eI.listar().stream().map(y->m.map(y,EstateDTO.class))
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

    @GetMapping("/filtro/{f1}/{f2}/{t1}")
    public List<Estate> filtroEstate(@PathVariable String f1, @PathVariable String f2, @PathVariable String t1){
        return eI.filtrarInmueblesPorCiudadDistritoTipo(f1, f2, t1);
    }

    @GetMapping("/amountT")
    public Double amountTotalPrice(){
            return eI.amountTotal();
    }

    @GetMapping("/owners-estates")
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

    @GetMapping("/avg-price-by-city")
    public ResponseEntity<?> avgPriceByCityAndType() {
        List<Object[]> resultados = eI.findAvgPriceByCityAndType();
        List<EstateAvgPriceDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            EstateAvgPriceDTO dto = new EstateAvgPriceDTO();
            dto.setCity(row[0].toString());
            dto.setType(row[1].toString());
            dto.setAveragePrice(((Number) row[2]).doubleValue());
            dto.setQuantity(((Number) row[3]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/best-price-per-room")
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

    @GetMapping("/available-districts")
    public ResponseEntity<?> availableDistricts() {
        List<Object[]> resultados = eI.findDistrictsWithMostAvailableEstates();
        List<EstateDistrictAvailabilityDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            EstateDistrictAvailabilityDTO dto = new EstateDistrictAvailabilityDTO();
            dto.setDistrict(row[0].toString());
            dto.setAvailableEstates(((Number) row[1]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/price-range-distribution")
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
