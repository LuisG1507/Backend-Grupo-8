package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import java.util.List;

public interface IReviewsService {
    public void insert(Reviews review);
    public List<Reviews> list();
    public void delete(Integer id);
    public Reviews listId(Integer id);
    public void update(Reviews review);
    public List<Reviews> listByMinRating(Double minRating);
    List<Object[]> findEstatesBelowAverageRating();
    List<Object[]> findLessorsWithBestRating();
    List<Object[]> findEstatesWithNoReviews();
    List<Object[]> findRatingDistribution();
}