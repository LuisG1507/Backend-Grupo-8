package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.*;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Models3DDTO;
import pe.edu.pe.smartrent_backend.Entities.Favorite;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IFavorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/Favorite")
public class FavoriteController {

    @Autowired
    private IFavorite fC;

    //Register
    @PostMapping
    public ResponseEntity<?> Register(@RequestBody FavoriteDTO fD){
        try{
            ModelMapper m = new ModelMapper();
            Favorite p = m.map(fD, Favorite.class);
            fC.Register(p);
            return ResponseEntity.ok("Favorite ha sido registrado correctamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error en el registro, vuelva a intentar");
        }
    }

    @PutMapping
    public ResponseEntity<?> Update(@RequestBody FavoriteCompleteDTO fD){
        Optional<Favorite> exist = fC.listId(fD.getIdFavorite());
        if(exist.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El valor no existe");
        }
        Favorite m = exist.get();
        m.setCreationDate(fD.getCreationDate());
        m.setUser(fD.getUser());
        m.setEstate(fD.getEstate());
        fC.Update(m);

        return ResponseEntity.ok("Su valor ha sido actualizado");
    }

    @GetMapping
    public ResponseEntity<?> ListFavorite(){
        ModelMapper m = new ModelMapper();
        List<FavoriteDTO> list = fC.list().stream().map(y->m.map(y,FavoriteDTO.class))
                .collect(Collectors.toList());
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay listas en este objeto");
        }else{
            return ResponseEntity.ok(list);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Optional<Favorite> exist = fC.listId(id);
        if(exist.isPresent()){
            fC.Delete(id);
            return ResponseEntity.ok("El valor ha sido eliminado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el valor ingresado");
        }
    }

    @GetMapping("/most-demanded")
    public ResponseEntity<List<FavoriteEstateDTO>> getMostDemanded() {
        List<Object[]> resultados = fC.findMostFavoritedEstates();
        List<FavoriteEstateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            FavoriteEstateDTO dto = new FavoriteEstateDTO();
            dto.setTitle(row[0].toString());
            dto.setCity(row[1].toString());
            dto.setDistrict(row[2].toString());
            dto.setMonthlyPrice(((Number) row[3]).doubleValue());
            dto.setTimesFavorite(((Number) row[4]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/unconverted-demand")
    public ResponseEntity<List<FavoriteNoContractDTO>> getUnconvertedDemand() {
        List<Object[]> resultados = fC.findFavoritedEstatesWithoutContract();
        List<FavoriteNoContractDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            FavoriteNoContractDTO dto = new FavoriteNoContractDTO();
            dto.setTitle(row[0].toString());
            dto.setCity(row[1].toString());
            dto.setFavorites(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/most-active-users")
    public ResponseEntity<List<FavoriteUsersDTO>> getMostActiveUsers() {
        List<Object[]> resultados = fC.findMostActiveUsers();
        List<FavoriteUsersDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            FavoriteUsersDTO dto = new FavoriteUsersDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setTotalFavorites(((Number) row[2]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/monthly-trends")
    public ResponseEntity<List<FavoriteMonthlyTrendDTO>> getMonthlyTrends() {
        List<Object[]> resultados = fC.findMonthlyTrend();
        List<FavoriteMonthlyTrendDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            FavoriteMonthlyTrendDTO dto = new FavoriteMonthlyTrendDTO();
            dto.setMonth(row[0].toString());
            dto.setFavoritesAdded(((Number) row[1]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

}
