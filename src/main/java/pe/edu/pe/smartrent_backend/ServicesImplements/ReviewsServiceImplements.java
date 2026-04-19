package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Reviews;
import pe.edu.pe.smartrent_backend.Repositories.ReviewsRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IReviewsService;

import java.util.List;

@Service
public class ReviewsServiceImplements implements IReviewsService {

    @Autowired
    private ReviewsRepository rR;

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
        return rR.findById(id).orElse(null);
    }

    @Override
    public void update(Reviews review) {
        rR.save(review);
    }
}