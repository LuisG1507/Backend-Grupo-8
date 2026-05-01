package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;

import java.util.List;

@Repository
public interface IRiskReportRepository extends JpaRepository<RiskReport,Integer> {

    //Modelos 3D con mayor cantidad de puntos de riesgo registrados
    @Query(value = "SELECT e.title, e.city, COUNT(rr.id_risk_report) AS total_reportes\n" +
            "FROM risk_report rr\n" +
            "INNER JOIN estate e ON rr.id_estate = e.id_estate\n" +
            "GROUP BY e.id_estate, e.title, e.city\n" +
            "ORDER BY total_reportes DESC\n", nativeQuery = true)
    List<Object[]> RRD1();

    //Distribución de puntos de riesgo por severidad con porcentaje


    @Query(value = "SELECT severity,\n" +
            "       COUNT(*) AS total,\n" +
            "       ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM risk_points), 2) AS porcentaje\n" +
            "FROM risk_points\n" +
            "GROUP BY severity\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> RRD2();

}
