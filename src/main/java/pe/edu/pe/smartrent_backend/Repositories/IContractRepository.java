package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractEstateRotationDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractExpiringDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractLessorContractRateDTO;
import pe.edu.pe.smartrent_backend.DTOS.contractDTOS.ContractLessorIncomeDTO;
import pe.edu.pe.smartrent_backend.Entities.Contract;

import java.util.List;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value = "SELECT u.name, u.last_name, SUM(c.monthly_amount) AS ingresos_totales\n" +
            "FROM contract c\n" +
            "INNER JOIN users u ON c.id_arrendador = u.id_user\n" +
            "WHERE c.status = true\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "HAVING SUM(c.monthly_amount) > (\n" +
            "    SELECT AVG(monthly_amount) FROM contract WHERE status = true\n" +
            ")\n" +
            "ORDER BY ingresos_totales DESC", nativeQuery = true)
    List<Object[]> findLessorsAboveAverageIncome();

    @Query(value = "SELECT u.name, u.last_name,\n" +
            "       SUM(CASE WHEN c.status = true THEN 1 ELSE 0 END) AS activos,\n" +
            "       SUM(CASE WHEN c.status = false THEN 1 ELSE 0 END) AS inactivos,\n" +
            "       COUNT(c.id_contract) AS total\n" +
            "FROM contract c\n" +
            "INNER JOIN users u ON c.id_arrendador = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> findContractRatePerLessor();

    @Query(value = "SELECT e.title, e.city, e.district, COUNT(c.id_contract) AS total_contratos\n" +
            "FROM contract c\n" +
            "INNER JOIN estate e ON c.id_estate = e.id_estate\n" +
            "GROUP BY e.id_estate, e.title, e.city, e.district\n" +
            "ORDER BY total_contratos DESC", nativeQuery = true)
    List<Object[]> findEstatesWithHighestRotation();

    @Query(value = "SELECT u.name, u.last_name, e.title, c.end_date,\n" +
            "       (c.end_date::date - CURRENT_DATE) AS dias_restantes\n" +
            "FROM contract c\n" +
            "INNER JOIN users u ON c.id_arrendatario = u.id_user\n" +
            "INNER JOIN estate e ON c.id_estate = e.id_estate\n" +
            "WHERE c.status = true\n" +
            "AND c.end_date::date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'\n" +
            "ORDER BY dias_restantes ASC\n", nativeQuery = true)
    List<Object[]> findContractsExpiringSoon();
}
