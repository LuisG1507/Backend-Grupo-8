package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
}

