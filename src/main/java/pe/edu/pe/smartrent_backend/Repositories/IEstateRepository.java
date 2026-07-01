package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.estateDTOS.*;
import pe.edu.pe.smartrent_backend.Entities.Estate;

import java.util.List;

@Repository
public interface IEstateRepository extends JpaRepository<Estate, Integer> {

    List<Estate> findByUserUsername(String username);

    @Query(value = "SELECT * FROM estate " +
            "WHERE LOWER(TRIM(city)) = LOWER(TRIM(:city)) " +
            "AND LOWER(TRIM(district)) = LOWER(TRIM(:district)) " +
            "AND LOWER(TRIM(type)) = LOWER(TRIM(:type))", nativeQuery = true)
    List<Estate> filtroCityDistrictType(
            @Param("city") String city,
            @Param("district") String district,
            @Param("type") String type);


    @Query(value = "SELECT u.name, u.last_name, e.rooms, e.monthly_price\n" +
            "FROM estate e\n" +
            "INNER JOIN users u ON e.id_user = u.id_user\n" +
            "WHERE e.rooms >= 3\n", nativeQuery = true)
    List<Object[]> listUserEstate();

    @Query(value = "SELECT  u.name, u.last_name, e.city, e.district, e.monthly_price FROM estate e\n" +
            "LEFT JOIN users u ON e.id_user = u.id_user\n" +
            "WHERE e.district = :dist ", nativeQuery = true)
    List<Object[]> listUserEstateDistrict(String dist);

    @Query(value = "SELECT \n" +
            "    e.title,\n" +
            "    e.district,\n" +
            "    e.monthly_price,\n" +
            "    e.rooms\n" +
            "FROM estate e\n" +
            "WHERE e.monthly_price > (SELECT AVG(monthly_price) FROM estate)\n" +
            "ORDER BY e.monthly_price DESC;", nativeQuery = true)
    List<Object[]> aar();


    @Query(value = "SELECT title, city, rooms, monthly_price,\n" +
            "       ROUND((monthly_price / rooms)::numeric, 2) AS precio_por_cuarto\n" +
            "FROM estate\n" +
            "ORDER BY precio_por_cuarto ASC", nativeQuery = true)
    List<Object[]> findBestPricePerRoom();


    @Query(value = "SELECT type,\n" +
            "       SUM(CASE WHEN monthly_price < 500 THEN 1 ELSE 0 END) AS rango_bajo,\n" +
            "       SUM(CASE WHEN monthly_price BETWEEN 500 AND 1000 THEN 1 ELSE 0 END) AS rango_medio,\n" +
            "       SUM(CASE WHEN monthly_price > 1000 THEN 1 ELSE 0 END) AS rango_alto\n" +
            "FROM estate\n" +
            "GROUP BY type\n" +
            "ORDER BY type", nativeQuery = true)
    List<Object[]> findDistributionByTypeAndPriceRange();


}
