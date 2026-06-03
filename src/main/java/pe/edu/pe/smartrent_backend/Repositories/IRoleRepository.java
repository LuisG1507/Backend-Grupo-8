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

    @Modifying
    @Query("DELETE FROM Role r WHERE r.id = :id")
    void deleteRoleDirecto(@Param("id") Integer id);
}
