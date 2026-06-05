package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.*;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Model3DIdDTO;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.Models3DDTO;
import pe.edu.pe.smartrent_backend.Entities.Favorite;
import pe.edu.pe.smartrent_backend.Entities.Models3D;
import pe.edu.pe.smartrent_backend.Entities.User;
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
    @PreAuthorize("hasAnyAuthority('ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<?> Register(@RequestBody FavoriteDTO fD){
            ModelMapper m = new ModelMapper();
            Favorite p = m.map(fD, Favorite.class);
            fC.Register(p);
            return ResponseEntity.ok("Favorite ha sido registrado correctamente");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<?> ListFavorite(){
        List<Favorite> favorites = fC.list();

        if(favorites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay listas en este objeto");
        }

        List<FavoriteDTOInfinite> list = favorites.stream().map(fav -> {
            FavoriteDTOInfinite dto = new FavoriteDTOInfinite();
            dto.setCreationDate(fav.getCreationDate());

            FavoriteDTOInfinite.UserBasicDTO userDTO = new FavoriteDTOInfinite.UserBasicDTO();
            userDTO.setIdUser(fav.getUser().getIdUser());
            userDTO.setUsername(fav.getUser().getUsername());
            dto.setUser(userDTO);

            FavoriteDTOInfinite.EstateBasicDTO estateDTO = new FavoriteDTOInfinite.EstateBasicDTO();
            estateDTO.setIdEstate(fav.getEstate().getIdEstate());
            estateDTO.setTitle(fav.getEstate().getTitle());
            estateDTO.setLocation(fav.getEstate().getAdress());
            dto.setEstate(estateDTO);

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Optional<Favorite> exist = fC.listId(id);
        if(exist.isPresent()){
            fC.Delete(id);
            return ResponseEntity.ok("El valor ha sido eliminado");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el valor ingresado");
        }
    }

    //listar por id
    @GetMapping("/listId/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Favorite favorite = fC.ListarId(id);

        if (favorite != null) {
            FavoriteIdDTO dto = m.map(favorite, FavoriteIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existen favoritos");
        }
    }





}
