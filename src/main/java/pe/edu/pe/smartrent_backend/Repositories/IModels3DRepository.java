package pe.edu.pe.smartrent_backend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

@Repository
public interface IModels3DRepository extends JpaRepository<Models3D, Integer> {
}
