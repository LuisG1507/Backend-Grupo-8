package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.UsersBackground;

import java.util.List;

@Repository
public interface IUserBackgroundRepository extends JpaRepository<UsersBackground,Integer> {

    @Query(value = "SELECT type,\n" +
            "       COUNT(*) AS total,\n" +
            "       ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM users_background), 2) AS porcentaje\n" +
            "FROM users_background\n" +
            "GROUP BY type\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> userBackgroundType();

    @Query(value = "SELECT u.name, u.last_name, COUNT(ub.id_background) AS total_antecedentes\n" +
            "FROM users_background ub\n" +
            "INNER JOIN users u ON ub.id_user = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "HAVING COUNT(ub.id_background) > (\n" +
            "    SELECT AVG(cnt) FROM (\n" +
            "        SELECT COUNT(*) AS cnt FROM users_background GROUP BY id_user\n" +
            "    ) sub\n" +
            ")\n" +
            "ORDER BY total_antecedentes DESC", nativeQuery = true)
    List<Object[]> findHighRiskUsers();
}
