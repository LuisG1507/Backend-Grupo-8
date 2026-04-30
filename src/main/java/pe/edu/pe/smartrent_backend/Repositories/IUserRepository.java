package pe.edu.pe.smartrent_backend.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {


    User findOneByUsername(String username);

    @Query("select count(u.username) from User u where u.username =:username")
    int buscarUsername(@Param("username") String nombre);

    @Transactional
    @Modifying
    @Query(value = "insert into roles (rol, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
    void insRol(@Param("rol") String authority, @Param("user_id") Integer user_id);

    @Query("SELECT u FROM User u WHERE u.dni = :d")
    User findByDNI(Integer d);

    @Query("SELECT u FROM User u WHERE u.statusVerification = true")
    List<User> findByStatusVerification();

    @Query("SELECT u FROM User u WHERE u.createdDate BETWEEN :f1 AND :f2")
    List<User> findByCreatedDateBetween(LocalDate f1, LocalDate f2);


    @Query(value = "SELECT \n" +
            "    u.name || ' ' || u.last_name usuario,\n" +
            "    COUNT(ub.id_background) AS total\n" +
            "FROM users u\n" +
            "LEFT JOIN users_background ub ON u.id_user = ub.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total DESC;", nativeQuery = true)
    List<Object[]> rankingDeIncidencias();
}
