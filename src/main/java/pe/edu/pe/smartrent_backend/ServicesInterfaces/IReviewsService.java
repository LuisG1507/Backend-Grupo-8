package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import java.util.List;

public interface IReviewsService {
    public void insert(Reviews review);
    public List<Reviews> list();
    public void delete(Integer id);
    public Reviews listId(Integer id);
    public void update(Reviews review);
    public List<Object[]> findEstatesBelowAverageRating();
    public List<Object[]> findLessorsWithBestRating();
    public List<Object[]> findEstatesWithNoReviews();
    public List<Object[]> findRatingDistribution();
}