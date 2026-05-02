package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.RiskPoints;

import java.util.List;

@Repository
public interface RiskPointsRepository extends JpaRepository<RiskPoints, Integer> {

    //Modelos 3D con mayor cantidad de puntos de riesgo registrados
    @Query(value = "SELECT m.id_models3d, e.title, e.city, COUNT(rp.id_risk_points) AS total_puntos\n" +
            "FROM risk_points rp\n" +
            "INNER JOIN models3d m ON rp.id_models3d = m.id_models3d\n" +
            "INNER JOIN estate e ON m.id_estate = e.id_estate\n" +
            "GROUP BY m.id_models3d, e.title, e.city\n" +
            "ORDER BY total_puntos DESC",nativeQuery = true)
    List<Object[]> RPD1();


    // Distribución de puntos de riesgo por severidad con porcentaje
    @Query(value = "SELECT severity,\n" +
            "       COUNT(*) AS total,\n" +
            "       ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM risk_points), 2) AS porcentaje\n" +
            "FROM risk_points\n" +
            "GROUP BY severity\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> RPD2();


    //Inmuebles con más puntos de severidad crítica (prioridad de intervención)
    @Query(value = "SELECT e.title, e.city, e.district, COUNT(rp.id_risk_points) AS puntos_criticos\n" +
            "FROM risk_points rp\n" +
            "INNER JOIN models3d m ON rp.id_models3d = m.id_models3d\n" +
            "INNER JOIN estate e ON m.id_estate = e.id_estate\n" +
            "WHERE rp.severity = 'CRITICO'\n" +
            "GROUP BY e.id_estate, e.title, e.city, e.district\n" +
            "HAVING COUNT(rp.id_risk_points) > (\n" +
            "    SELECT AVG(cnt) FROM (\n" +
            "        SELECT COUNT(*) AS cnt FROM risk_points WHERE severity = 'CRITICO'\n" +
            "        GROUP BY id_models3d\n" +
            "    ) sub\n" +
            ")\n" +
            "ORDER BY puntos_criticos DESC", nativeQuery = true)
    List<Object[]> RPD3();


    // Modelos 3D sin ningún punto de riesgo registrado (posiblemente sin inspección)
    @Query(value = "SELECT m.id_models3d, e.title, e.city, m.create_date\n" +
            "FROM models3d m\n" +
            "INNER JOIN estate e ON m.id_estate = e.id_estate\n" +
            "LEFT JOIN risk_points rp ON m.id_models3d = rp.id_models3d\n" +
            "WHERE rp.id_risk_points IS NULL", nativeQuery = true)
    List<Object[]> RPD4();
}