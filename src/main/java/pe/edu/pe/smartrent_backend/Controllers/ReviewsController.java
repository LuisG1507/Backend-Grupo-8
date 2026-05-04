package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IEstate;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IReviewsService;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Reviews")
public class ReviewsController {

    @Autowired
    private IReviewsService rI;

    @Autowired
    private IUser uS;

    @Autowired
    private IEstate eS;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
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

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@PathVariable int id, @RequestBody ReviewsCompleteDTO rC) {

        Reviews exist = rI.listId(id);
        if (exist == null) {
            return new ResponseEntity<>("La reseña no fue encontrada", HttpStatus.NOT_FOUND);
        }

        User u = uS.listId(rC.getIdUser());
        if (u == null) return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);

        Estate e = eS.listarId(rC.getIdEstate()).orElse(null);
        if (e == null) return new ResponseEntity<>("Propiedad no encontrada", HttpStatus.NOT_FOUND);

        exist.setCalification(rC.getCalification());
        exist.setComment(rC.getComment());
        exist.setCreationDate(rC.getCreationDate());
        exist.setUser(u);
        exist.setEstate(e);

        rI.update(exist);
        return new ResponseEntity<>("Se ha actualizado de forma correcta", HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        Reviews exist = rI.listId(id);
        if (exist != null && exist.getIdReview() != null) {
            rI.delete(id);
            return new ResponseEntity<>("El valor ha sido eliminado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se ha encontrado el valor ingresado", HttpStatus.NOT_FOUND);
        }
    }

    // 1. Inmuebles con calificación por debajo del promedio general
    @GetMapping("/below-average")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<?> belowAverage() {
        List<Object[]> resultados = rI.findEstatesBelowAverageRating();
        List<ReviewsBelowAverageDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ReviewsBelowAverageDTO dto = new ReviewsBelowAverageDTO();
            dto.setTitle(row[0].toString());
            dto.setCity(row[1].toString());
            dto.setAverage(((Number) row[2]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    // Arrendadores con mejor calificación promedio en sus inmuebles
    @GetMapping("/best-lessors")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO', 'ARRENDADOR')")
    public ResponseEntity<?> bestLessors() {
        List<Object[]> resultados = rI.findLessorsWithBestRating();
        List<ReviewsLessorRatingDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ReviewsLessorRatingDTO dto = new ReviewsLessorRatingDTO();
            dto.setName(row[0].toString());
            dto.setLastName(row[1].toString());
            dto.setAverage(((Number) row[2]).doubleValue());
            dto.setTotalReviews(((Number) row[3]).longValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    // Inmuebles sin ninguna reseña (sin retroalimentación)
    @GetMapping("/no-reviews")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> noReviews() {
        List<Object[]> resultados = rI.findEstatesWithNoReviews();
        List<ReviewsNoReviewEstateDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ReviewsNoReviewEstateDTO dto = new ReviewsNoReviewEstateDTO();
            dto.setIdEstate(((Number) row[0]).intValue());
            dto.setTitle(row[1].toString());
            dto.setCity(row[2].toString());
            dto.setMonthlyPrice(((Number) row[3]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }

    //Distribución de calificaciones en la plataforma


    @GetMapping("/rating-distribution")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> ratingDistribution() {
        List<Object[]> resultados = rI.findRatingDistribution();
        List<ReviewsRatingDistributionDTO> lista = new ArrayList<>();
        for (Object[] row : resultados) {
            ReviewsRatingDistributionDTO dto = new ReviewsRatingDistributionDTO();
            dto.setBad(((Number) row[0]).longValue());
            dto.setRegular(((Number) row[1]).longValue());
            dto.setGood(((Number) row[2]).longValue());
            dto.setGlobalAverage(((Number) row[3]).doubleValue());
            lista.add(dto);
        }
        return ResponseEntity.ok(lista);
    }
}
