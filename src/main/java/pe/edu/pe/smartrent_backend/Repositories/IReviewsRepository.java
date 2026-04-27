package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import java.util.List;

@Repository
public interface IReviewsRepository extends JpaRepository<Reviews, Integer> {

    @org.springframework.data.jpa.repository.Query("SELECT r FROM Reviews r WHERE r.calification >= :minRating")
    List<Reviews> findByMinRating(@org.springframework.data.repository.query.Param("minRating") Double minRating);

    @org.springframework.data.jpa.repository.Query("SELECT new pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.EstateAverageRatingDTO(e.title, AVG(r.calification)) " +
            "FROM Reviews r JOIN r.estate e " +
            "GROUP BY e.title")
    List<pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.EstateAverageRatingDTO> getAverageRatingPerEstate();

}