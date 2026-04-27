package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS.EstateAverageRatingDTO;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IReviewsService;
import pe.edu.pe.smartrent_backend.Repositories.IReviewsRepository; // <-- IMPORTANTE

import java.util.List;

@Service
public class ReviewsServiceImplements implements IReviewsService {

    @Autowired
    private IReviewsRepository rR; // <-- AQUÍ estaba el error, debe ser IReviewsRepository

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
    public List<Reviews> listByMinRating(Double minRating) {
        return rR.findByMinRating(minRating);
    }

    @Override
    public List<EstateAverageRatingDTO> getAverageRatingPerEstate() {
        return rR.getAverageRatingPerEstate();
    }
}