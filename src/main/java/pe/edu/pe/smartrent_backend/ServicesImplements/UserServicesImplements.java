package pe.edu.pe.smartrent_backend.ServicesImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.Repositories.IRoleRepository;
import pe.edu.pe.smartrent_backend.Repositories.IUserRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.time.LocalDate;
import java.util.List;


@Service
public class UserServicesImplements implements IUser {

    @Autowired
    private IUserRepository uR;

    @Autowired
    private IRoleRepository rR;


    @Override
    public void Register(User user) {
        uR.save(user);
    }

    @Override
    public void Update(User user) {
        uR.save(user);
    }

    @Override
    public User listId(Integer id) {
        return uR.findById(id).orElse(null);
    }

    @Override
    public List<User> list() {
        return uR.findAll();
    }

    @Override
    public void Delete(Integer id) {
        User user = uR.findById(id).orElseThrow();
        user.getRoles().clear();
        uR.save(user);
        uR.deleteById(id);
    }

    @Override
    public User BuscarPorDNI(Integer id) {
        return uR.findByDNI(id);
    }

    @Override
    public List<Object[]> findUnverifiedUsersWithBackgrounds() {
        return uR.findUnverifiedUsersWithBackgrounds();
    }

}

