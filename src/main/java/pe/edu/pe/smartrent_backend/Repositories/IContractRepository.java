package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Contract;

import java.util.List;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value = "SELECT u.name, u.last_name, e.title, c.end_date,\n" +
            "       (c.end_date::date - CURRENT_DATE) AS dias_restantes\n" +
            "FROM contract c\n" +
            "INNER JOIN users u ON c.id_arrendatario = u.id_user\n" +
            "INNER JOIN estate e ON c.id_estate = e.id_estate\n" +
            "WHERE c.status = true\n" +
            "AND c.end_date::date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'\n" +
            "ORDER BY dias_restantes ASC\n", nativeQuery = true)
    List<Object[]> findContractsExpiringSoon();

    //Luciana
    // 1. Ingresos por distrito (Agregamos alias "district" y "totalRevenue")
    @Query(value = "SELECT e.district AS district, SUM(c.monthly_amount) AS totalRevenue " +
            "FROM contract c " +
            "JOIN estate e ON c.id_estate = e.id_estate " +
            "GROUP BY e.district " +
            "ORDER BY totalRevenue DESC", nativeQuery = true)
    List<Object[]> findRevenueByDistrict();

    // 2. Duración promedio (Agregamos alias "name", "lastName", "avgDays")
    @Query(value = "SELECT u.name AS name, u.last_name AS lastName, AVG(EXTRACT(DAY FROM (c.end_date - c.start_date))) AS avgDays " +
            "FROM contract c " +
            "JOIN users u ON c.id_arrendador = u.id_user " +
            "GROUP BY u.id_user, u.name, u.last_name", nativeQuery = true)
    List<Object[]> findAverageContractDurationByLessor();
}
