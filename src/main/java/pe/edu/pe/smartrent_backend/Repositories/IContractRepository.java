package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Contract;

import java.util.List;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value ="SELECT \n" +
            " u.id_user,\n" +
            " u.name,\n" +
            " COUNT(c.id_contract),\n" +
            " COALESCE(SUM(c.monthly_amount), 0)\n" +
            " FROM contract c\n" +
            " INNER JOIN users u ON c.id_arrendador = u.id_user\n" +
            " WHERE c.status = true\n" +
            " GROUP BY u.id_user, u.name\n" +
            " ORDER BY COALESCE(SUM(c.monthly_amount), 0) DESC", nativeQuery = true)
    List<Object[]> getIncomeByLessor();

    @Query(value ="SELECT\n" +
            " e.id_estate,\n" +
            " e.title\n" +
            " FROM estate e\n" +
            " LEFT JOIN contract c \n" +
            " ON e.id_estate = c.id_estate\n" +
            " AND c.status = true\n" +
            " WHERE c.id_contract IS NULL\n" +
            " ORDER BY e.id_estate" , nativeQuery = true)
    List<Object[]> getEstatesWithoutActiveContract();
}
