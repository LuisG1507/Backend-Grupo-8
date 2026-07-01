package pe.edu.pe.smartrent_backend.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        String validationError = validarResena(
                rD.getCalification(), rD.getComment(), rD.getCreationDate(),
                rD.getIdUser(), rD.getIdEstate());
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
        }
        if (uS.listId(rD.getIdUser()) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
        if (eS.listarId(rD.getIdEstate()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inmueble no encontrado.");
        }

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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ARRENDATARIO')")
    public ResponseEntity<?> listarTodo() {
        ModelMapper m = new ModelMapper();
        List<ReviewsCompleteDTO> list = rI.list().stream().map(y -> {
            ReviewsCompleteDTO dto = m.map(y, ReviewsCompleteDTO.class);
            dto.setIdReview(y.getIdReview());
            dto.setIdUser(y.getUser().getIdUser());
            dto.setIdEstate(y.getEstate().getIdEstate());
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // El username sale del token, por eso el arrendador no puede consultar datos de otro propietario.
    @GetMapping("/my-reviews")
    @PreAuthorize("hasAuthority('ARRENDADOR')")
    public ResponseEntity<?> listarMisResenias(Authentication authentication) {
        ModelMapper m = new ModelMapper();
        List<ReviewsCompleteDTO> list = rI.listByUsername(authentication.getName()).stream().map(y -> {
            ReviewsCompleteDTO dto = m.map(y, ReviewsCompleteDTO.class);
            dto.setIdReview(y.getIdReview());
            dto.setIdUser(y.getUser().getIdUser());
            dto.setIdEstate(y.getEstate().getIdEstate());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> actualizar(@PathVariable int id, @RequestBody ReviewsCompleteDTO rC) {

        Reviews exist = rI.listId(id);
        if (exist == null) {
            return new ResponseEntity<>("La reseña no fue encontrada", HttpStatus.NOT_FOUND);
        }

        String validationError = validarResena(
                rC.getCalification(), rC.getComment(), rC.getCreationDate(),
                rC.getIdUser(), rC.getIdEstate());
        if (validationError != null) {
            return ResponseEntity.badRequest().body(validationError);
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

    //listar por id
    @GetMapping("/listId/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Reviews reviews = rI.listId(id);

        if (reviews != null) {
            ReviewsIdDTO dto = m.map(reviews, ReviewsIdDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Puntos de riesgo no encontrado");
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

    private String validarResena(
            Double calification, String comment, java.time.LocalDate creationDate,
            Integer idUser, Integer idEstate) {
        if (calification == null || calification < 1 || calification > 5) {
            return "La calificacion debe estar entre 1 y 5.";
        }
        if (comment == null || comment.trim().length() < 5 || comment.trim().length() > 200) {
            return "El comentario debe contener entre 5 y 200 caracteres.";
        }
        if (creationDate == null || creationDate.isAfter(java.time.LocalDate.now())) {
            return "La fecha de la resena no puede estar en el futuro.";
        }
        if (idUser == null || idUser <= 0 || idEstate == null || idEstate <= 0) {
            return "Seleccione un usuario y un inmueble validos.";
        }
        return null;
    }
}
