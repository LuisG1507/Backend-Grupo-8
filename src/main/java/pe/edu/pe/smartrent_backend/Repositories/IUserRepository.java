package pe.edu.pe.smartrent_backend.Repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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
}
