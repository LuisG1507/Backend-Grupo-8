package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.OwnerEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.UserEstateDTO;
import pe.edu.pe.smartrent_backend.Entities.Estate;

import java.util.List;

@Repository
public interface IEstateRepository extends JpaRepository<Estate, Integer> {

    @Query(value = "SELECT * FROM estate\n" +
            "WHERE city = :f1 AND district = :f2 AND type = :t1", nativeQuery = true)
    List<Estate> filtroCityDistrictType(@Param("f1") String f1,@Param("f2") String f2,@Param("t1") String t1);

    @Query(value = "SELECT SUM(monthly_price) AS TotalPrice \n" +
            "FROM estate\n" +
            "WHERE rooms >= 3", nativeQuery = true)
    Double SumTotal();

    @Query(value = "SELECT u.name, u.last_name, e.rooms, e.monthly_price\n" +
            "FROM estate e\n" +
            "INNER JOIN users u ON e.id_user = u.id_user\n" +
            "WHERE e.rooms >= 3\n", nativeQuery = true)
    List<OwnerEstateDTO> listUserEstate();

    @Query(value = "SELECT  u.name, u.last_name, e.city, e.district, e.monthly_price FROM estate e\n" +
            "LEFT JOIN users u ON e.id_user = u.id_user\n" +
            "WHERE e.district = :dist ", nativeQuery = true)
    List<UserEstateDTO> listUserEstateDistrict(String dist);

    @Query(value = "SELECT \n" +
            "    e.title,\n" +
            "    e.district,\n" +
            "    e.monthly_price,\n" +
            "    e.rooms\n" +
            "FROM estate e\n" +
            "WHERE e.monthly_price > (SELECT AVG(monthly_price) FROM estate)\n" +
            "ORDER BY e.monthly_price DESC;", nativeQuery = true)
    List<Object[]> aar();


}
