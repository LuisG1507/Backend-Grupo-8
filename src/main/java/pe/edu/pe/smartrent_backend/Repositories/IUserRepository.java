package pe.edu.pe.smartrent_backend.Repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserEnabledByRoleDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserMonthlyGrowthDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserUnverifiedWithBackgroundDTO;
import pe.edu.pe.smartrent_backend.DTOS.userDTOS.UserVerificationStatsDTO;
import pe.edu.pe.smartrent_backend.Entities.Users;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.dni = :d")
    Users findByDNI(Integer d);

    @Query("SELECT u FROM Users u WHERE u.statusVerification = true")
    List<Users> findByStatusVerification();

    @Query("SELECT u FROM Users u WHERE u.createdDate BETWEEN :f1 AND :f2")
    List<Users> findByCreatedDateBetween(LocalDate f1, LocalDate f2);


    @Query(value = "SELECT \n" +
            "    u.name || ' ' || u.last_name usuario,\n" +
            "    COUNT(ub.id_background) AS total\n" +
            "FROM users u\n" +
            "LEFT JOIN users_background ub ON u.id_user = ub.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> rankingDeIncidencias();

    @Query(value = "SELECT\n" +
            "    SUM(CASE WHEN status_verification = true THEN 1 ELSE 0 END) AS verificados,\n" +
            "    SUM(CASE WHEN status_verification = false THEN 1 ELSE 0 END) AS no_verificados,\n" +
            "    ROUND(SUM(CASE WHEN status_verification = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS porcentaje_verificados\n" +
            "FROM users", nativeQuery = true)
    List<Object[]> findVerificationStats();

    @Query(value = "SELECT u.name, u.last_name, COUNT(ub.id_background) AS total_antecedentes\n" +
            "FROM users u\n" +
            "INNER JOIN users_background ub ON u.id_user = ub.id_user\n" +
            "WHERE u.status_verification = false\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total_antecedentes DESC", nativeQuery = true)
    List<UserUnverifiedWithBackgroundDTO> findUnverifiedUsersWithBackgrounds();

    @Query(value = "SELECT DATE_TRUNC('month', created_date) AS mes,\n" +
            "       COUNT(*) AS nuevos_usuarios\n" +
            "FROM users\n" +
            "GROUP BY mes\n" +
            "ORDER BY mes DESC", nativeQuery = true)
    List<Object[]> findMonthlyGrowth();

    @Query(value = "SELECT r.type, \n" +
            "       SUM(CASE WHEN u.enabled = true THEN 1 ELSE 0 END) AS habilitados,\n" +
            "       SUM(CASE WHEN u.enabled = false THEN 1 ELSE 0 END) AS deshabilitados\n" +
            "FROM users u\n" +
            "INNER JOIN user_rol ur ON u.id_user = ur.user_id\n" +
            "INNER JOIN roles r ON ur.role_id = r.id\n" +
            "GROUP BY r.type", nativeQuery = true)
    List<Object[]> findEnabledUsersByRole();


}
