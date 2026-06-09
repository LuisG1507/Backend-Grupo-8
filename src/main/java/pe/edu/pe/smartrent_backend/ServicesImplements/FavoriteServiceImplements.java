package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Favorite;
import pe.edu.pe.smartrent_backend.Repositories.IFavoriteRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IFavorite;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImplements implements IFavorite {

    @Autowired
    IFavoriteRepository fR;
    @Override
    public void Register(Favorite fav) {
        fR.save(fav);
    }

    @Override
    public void Update(Favorite favorite) {
        fR.save(favorite);
    }

    @Override
    public Optional<Favorite> listId(Integer id) {
        return fR.findById(id);
    }

    @Override
    public Favorite ListarId(Integer id) {
        return fR.findById(id).orElse(null);
    }

    @Override
    public List<Favorite> list() {
        return fR.findAll();
    }

    @Override
    public void Delete(Integer id) {
        fR.deleteById(id);
    }


}
