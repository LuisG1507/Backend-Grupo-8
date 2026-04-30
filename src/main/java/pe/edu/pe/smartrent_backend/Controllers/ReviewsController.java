package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsCompleteDTO;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IReviewsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Reviews")
public class ReviewsController {

    @Autowired
    private IReviewsService rI;

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody ReviewsDTO rD) {
        ModelMapper m = new ModelMapper();
        Reviews r = m.map(rD, Reviews.class);

        User u = new User();
        u.setIdUser(rD.getIdUser());
        r.setUser(u);

        Estate e = new Estate();
        e.setIdEstate(rD.getIdEstate());
        r.setEstate(e);

        rI.insert(r);
        return new ResponseEntity<>("Registrado correctamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listarTodo() {
        ModelMapper m = new ModelMapper();
        List<ReviewsCompleteDTO> list = rI.list().stream().map(y -> {
            ReviewsCompleteDTO dto = m.map(y, ReviewsCompleteDTO.class);
            dto.setIdReview(y.getIdReview());
            dto.setIdUser(y.getUser().getIdUser());
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
    public ResponseEntity<String> actualizar(@RequestBody ReviewsCompleteDTO rC) {
        Reviews exist = rI.listId(rC.getIdReview());
        if (exist == null || exist.getIdReview() == null) {
            return new ResponseEntity<>("La reseña no fue encontrada", HttpStatus.NOT_FOUND);
        }

        exist.setCalification(rC.getCalification());
        exist.setComment(rC.getComment());
        exist.setCreationDate(rC.getCreationDate());

        User u = new User();
        u.setIdUser(rC.getIdUser());
        exist.setUser(u);

        Estate e = new Estate();
        e.setIdEstate(rC.getIdEstate());
        exist.setEstate(e);

        rI.update(exist);

        return new ResponseEntity<>("Se ha actualizado de forma correcta", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        Reviews exist = rI.listId(id);
        if (exist != null && exist.getIdReview() != null) {
            rI.delete(id);
            return new ResponseEntity<>("El valor ha sido eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se ha encontrado el valor ingresado", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filtro-calificacion/{minRating}")
    public List<ReviewsDTO> filtrar(@PathVariable Double minRating) {
        return rI.listByMinRating(minRating).stream().map(r -> {
            ReviewsDTO dto = new ReviewsDTO();
            dto.setCalification(r.getCalification());
            dto.setComment(r.getComment());
            dto.setCreationDate(r.getCreationDate());
            dto.setIdUser(r.getUser().getIdUser());
            dto.setIdEstate(r.getEstate().getIdEstate());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/reporte-promedios")
    public List<pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.EstateAverageRatingDTO> reportePromedios() {
        return rI.getAverageRatingPerEstate();
    }
}