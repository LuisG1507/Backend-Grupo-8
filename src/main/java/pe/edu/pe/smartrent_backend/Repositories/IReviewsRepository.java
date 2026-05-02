package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsBelowAverageDTO;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsLessorRatingDTO;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsNoReviewEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.ReviewsRatingDistributionDTO;
import pe.edu.pe.smartrent_backend.Entities.Reviews;

import java.util.List;
import java.util.Objects;

public interface IReviewsRepository extends JpaRepository<Reviews,Integer> {

    @Query(value = "SELECT e.title, e.city,\n" +
            "       ROUND(AVG(r.calification)::numeric, 2) AS promedio\n" +
            "FROM reviews r\n" +
            "INNER JOIN estate e ON r.id_estate = e.id_estate\n" +
            "GROUP BY e.id_estate, e.title, e.city\n" +
            "HAVING AVG(r.calification) < (SELECT AVG(calification) FROM reviews)\n" +
            "ORDER BY promedio ASC", nativeQuery = true)
    List<Object[]> findEstatesBelowAverageRating();

    @Query(value = "SELECT u.name, u.last_name,\n" +
            "       ROUND(AVG(r.calification)::numeric, 2) AS promedio,\n" +
            "       COUNT(r.id_review) AS total_resenas\n" +
            "FROM reviews r\n" +
            "INNER JOIN estate e ON r.id_estate = e.id_estate\n" +
            "INNER JOIN users u ON e.id_user = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY promedio DESC", nativeQuery = true)
    List<Object[]> findLessorsWithBestRating();

    @Query(value = "SELECT e.id_estate, e.title, e.city, e.monthly_price\n" +
            "FROM estate e\n" +
            "LEFT JOIN reviews r ON e.id_estate = r.id_estate\n" +
            "WHERE r.id_review IS NULL\n", nativeQuery = true)
    List<Object[]> findEstatesWithNoReviews();

    @Query(value = "SELECT\n" +
            "    SUM(CASE WHEN calification <= 2 THEN 1 ELSE 0 END) AS malas,\n" +
            "    SUM(CASE WHEN calification = 3 THEN 1 ELSE 0 END) AS regulares,\n" +
            "    SUM(CASE WHEN calification >= 4 THEN 1 ELSE 0 END) AS buenas,\n" +
            "    ROUND(AVG(calification)::numeric, 2) AS promedio_global\n" +
            "FROM reviews", nativeQuery = true)
    List<Object[]> findRatingDistribution();

    //
    @Query("SELECT r FROM Reviews r WHERE r.calification >= :minRating")
    List<Reviews> findByMinRating(@Param("minRating") Double minRating);
}
