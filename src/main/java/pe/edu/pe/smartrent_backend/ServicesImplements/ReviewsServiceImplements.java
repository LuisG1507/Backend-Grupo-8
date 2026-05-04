package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IReviewsService;
import pe.edu.pe.smartrent_backend.Repositories.IReviewsRepository; // <-- IMPORTANTE

import java.util.List;

@Service
public class ReviewsServiceImplements implements IReviewsService {

    @Autowired
    private IReviewsRepository rR; //

    @Override
    public void insert(Reviews review) {
        rR.save(review);
    }

    @Override
    public List<Reviews> list() {
        return rR.findAll();
    }

    @Override
    public void delete(Integer id) {
        rR.deleteById(id);
    }

    @Override
    public Reviews listId(Integer id) {
        return rR.findById(id).orElse(new Reviews());
    }

    @Override
    public void update(Reviews review) {
        rR.save(review);
    }

    @Override
    public List<Object[]> findEstatesBelowAverageRating() {
        return rR.findEstatesBelowAverageRating();
    }

    @Override
    public List<Object[]> findLessorsWithBestRating() {
        return rR.findLessorsWithBestRating();
    }

    @Override
    public List<Object[]> findEstatesWithNoReviews() {
        return rR.findEstatesWithNoReviews();
    }

    @Override
    public List<Object[]> findRatingDistribution() {
        return rR.findRatingDistribution();
    }

}