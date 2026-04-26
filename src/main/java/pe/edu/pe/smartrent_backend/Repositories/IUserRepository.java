package pe.edu.pe.smartrent_backend.Repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.Entities.Users;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.dni = :d")
    Users findByDNI(Integer d);



}
