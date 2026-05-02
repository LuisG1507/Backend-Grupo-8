package pe.edu.pe.smartrent_backend.ServicesInterfaces;

import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteEstateDTO;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteMonthlyTrendDTO;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteNoContractDTO;
import pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS.FavoriteUsersDTO;
import pe.edu.pe.smartrent_backend.Entities.Favorite;
import pe.edu.pe.smartrent_backend.Entities.Users;

import java.util.List;
import java.util.Optional;

public interface IFavorite {

    public void Register(Favorite fav);
    public void Update(Favorite favorite);
    public Optional<Favorite> listId(Integer id);
    public List<Favorite> list();
    public void Delete(Integer id);

    List<Object[]> findMostFavoritedEstates();
    List<Object[]> findFavoritedEstatesWithoutContract();
    List<Object[]> findMostActiveUsers();
    List<Object[]> findMonthlyTrend();
}
