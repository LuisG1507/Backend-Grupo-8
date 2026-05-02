package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.RiskReport;

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


    //Usuarios que más reportes han generado

    @Query(value = "SELECT u.name, u.last_name, COUNT(rr.id_risk_report) AS total_reportes\n" +
            "FROM risk_report rr\n" +
            "INNER JOIN users u ON rr.id_user = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total_reportes DESC\n",nativeQuery = true)
    List<Object[]> RRD3();


    //Inmuebles con nivel de riesgo ALTO que aún tienen contrato activo (situación crítica)

    @Query(value = "SELECT e.title, e.city, e.district, COUNT(rr.id_risk_report) AS reportes_altos\n" +
            "FROM risk_report rr\n" +
            "INNER JOIN estate e ON rr.id_estate = e.id_estate\n" +
            "INNER JOIN contract c ON e.id_estate = c.id_estate AND c.status = true\n" +
            "WHERE rr.risk_level = 'ALTO'\n" +
            "GROUP BY e.id_estate, e.title, e.city, e.district\n" +
            "ORDER BY reportes_altos DESC", nativeQuery = true)
    List<Object[]> RRD4();

}
