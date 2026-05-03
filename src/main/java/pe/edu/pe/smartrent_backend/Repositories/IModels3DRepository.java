package pe.edu.pe.smartrent_backend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.models3DDTOs.*;
import pe.edu.pe.smartrent_backend.Entities.Models3D;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IModels3DRepository extends JpaRepository<Models3D, Integer> {

    List<Models3D> findByState(String state);

    @Query(value = "SELECT * FROM models3d WHERE create_date = :fecha", nativeQuery = true)
    List<Models3D> buscarPorFecha(LocalDate fecha);

    @Query("SELECT m.fileURL,m.state,e.city,e.district " +
            "FROM Models3D m INNER JOIN m.estate e")
    List<Object[]> modelosConUbicacion();

    @Query("SELECT e.city,e.district,m.fileURL FROM Estate e LEFT JOIN Models3D m ON e.idEstate=m.estate.idEstate")
    List<Object[]> inmueblesConModelo();

    @Query(value = "SELECT e.id_estate, e.title, e.city, e.monthly_price\n" +
            "FROM estate e\n" +
            "LEFT JOIN models3d m ON e.id_estate = m.id_estate\n" +
            "WHERE m.id_models3d IS NULL\n" +
            "ORDER BY e.monthly_price DESC",nativeQuery = true)
    List<Object[]> findEstatesWithoutModel();

    @Query(value = "SELECT e.city, COUNT(m.id_models3d) AS modelos_activos\n" +
            "FROM models3d m\n" +
            "INNER JOIN estate e ON m.id_estate = e.id_estate\n" +
            "WHERE m.state = 'ACTIVO'\n" +
            "GROUP BY e.city\n" +
            "ORDER BY modelos_activos DESC\n",nativeQuery = true)
    List<Object[]> findCitiesWithMostActiveModels();


    @Query(value = "SELECT state, COUNT(*) AS total, " +
            "ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM models3d), 2) AS percentage " +
            "FROM models3d " +
            "GROUP BY state",
            nativeQuery = true)
    List<Object[]> findStateRate();


    @Query(value = "SELECT e.title, e.city, COUNT(rp.id_risk_points) AS puntos_criticos\n" +
            "FROM models3d m\n" +
            "INNER JOIN estate e ON m.id_estate = e.id_estate\n" +
            "INNER JOIN risk_points rp ON m.id_models3d = rp.id_models3d\n" +
            "WHERE m.state = 'ACTIVO'\n" +
            "AND rp.severity = 'CRITICO'\n" +
            "GROUP BY e.id_estate, e.title, e.city\n" +
            "ORDER BY puntos_criticos DESC",nativeQuery = true)
    List<Object[]> findEstatesWithCriticalRiskPoints();

}
