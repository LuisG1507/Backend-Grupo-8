package pe.edu.pe.smartrent_backend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.Models3DDTOs.ModelEstateDTO;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

import java.util.List;

@Repository
public interface IModels3DRepository extends JpaRepository<Models3D, Integer> {

    List<Models3D> findByState(String state);

    @Query(value = "SELECT * FROM models3d WHERE create_date > :fecha", nativeQuery = true)
    List<Models3D> buscarPorFecha(String fecha);

    @Query("SELECT m.fileURL,m.state,e.city,e.district " +
            "FROM Models3D m INNER JOIN m.estate e")
    List<Object[]> modelosConUbicacion();

    @Query("SELECT e.city,e.district,m.fileURL FROM Estate e LEFT JOIN Models3D m ON e.idEstate=m.estate.idEstate")
    List<Object[]> inmueblesConModelo();
}
