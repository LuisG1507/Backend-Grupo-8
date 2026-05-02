package pe.edu.pe.smartrent_backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteMonthlyTrendDTO;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteNoContractDTO;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteUsersDTO;
import pe.edu.pe.smartrent_backend.Entities.Favorite;

import java.util.List;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite, Integer> {

    @Query(value = "SELECT e.title, e.city, e.district, e.monthly_price, COUNT(f.id_favorite) AS veces_favorito\n" +
            "FROM favorite f\n" +
            "INNER JOIN estate e ON f.id_estate = e.id_estate\n" +
            "GROUP BY e.id_estate, e.title, e.city, e.district, e.monthly_price\n" +
            "ORDER BY veces_favorito DESC", nativeQuery = true)
    List<Object[]> findFavoriteEstate();

    @Query(value = "SELECT e.title, e.city, COUNT(f.id_favorite) AS en_favoritos\n" +
            "FROM favorite f\n" +
            "INNER JOIN estate e ON f.id_estate = e.id_estate\n" +
            "WHERE e.id_estate NOT IN (\n" +
            "    SELECT id_estate FROM contract WHERE status = true\n" +
            ")\n" +
            "GROUP BY e.id_estate, e.title, e.city\n" +
            "ORDER BY en_favoritos DESC", nativeQuery = true)
    List<Object[]> findFavoritedEstatesWithoutContract();

    @Query(value = "SELECT u.name, u.last_name, COUNT(f.id_favorite) AS total_favoritos\n" +
            "FROM favorite f\n" +
            "INNER JOIN users u ON f.id_user = u.id_user\n" +
            "GROUP BY u.id_user, u.name, u.last_name\n" +
            "ORDER BY total_favoritos DESC", nativeQuery = true)
    List<Object[]> findMostActiveUsers();

    @Query(value = "SELECT DATE_TRUNC('month', f.creation_date) AS mes,\n" +
            "       COUNT(*) AS favoritos_agregados\n" +
            "FROM favorite f\n" +
            "GROUP BY mes\n" +
            "ORDER BY mes DESC", nativeQuery = true)
    List<Object[]> findMonthlyTrend();

}
