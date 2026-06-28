package pe.edu.pe.smartrent_backend.ServicesInterfaces;
import pe.edu.pe.smartrent_backend.Controllers.FavoriteController;
import pe.edu.pe.smartrent_backend.Entities.Favorite;
import java.util.List;
import java.util.Optional;

public interface IFavorite {

    public void Register(Favorite fav);
    public void Update(Favorite favorite);
    public Optional<Favorite> listId(Integer id);
    public Favorite ListarId(Integer id);
    public List<Favorite> list();
    public void Delete(Integer id);


}
