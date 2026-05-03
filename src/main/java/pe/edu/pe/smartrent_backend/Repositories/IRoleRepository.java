package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Role;

import java.util.List;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Integer> {

    //Distribución de roles en la plataforma con porcentaje
    @Query(value = "SELECT rol,\n" +
            "       COUNT(*) AS total,\n" +
            "       ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM roles), 2) AS porcentaje\n" +
            "FROM roles\n" +
            "GROUP BY rol\n" +
            "ORDER BY total DESC", nativeQuery = true)
    List<Object[]> RDecision1();


    //Usuarios con más de un rol asignado

    @Query(value = "SELECT u.name, u.last_name, COUNT(r.id) AS cantidad_roles\n" +
            "FROM roles r\n" +
            "INNER JOIN users u ON r.user_id = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "HAVING COUNT(r.id) > 1\n" +
            "ORDER BY cantidad_roles DESC", nativeQuery = true)
    List<Object[]> RDecision2();


    @Modifying
    @Query("DELETE FROM Role r WHERE r.id = :id")
    void deleteRoleDirecto(@Param("id") Integer id);
}
