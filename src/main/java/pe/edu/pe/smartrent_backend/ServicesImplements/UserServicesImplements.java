package pe.edu.pe.smartrent_backend.ServicesImplements;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.User;
import pe.edu.pe.smartrent_backend.Repositories.IRoleRepository;
import pe.edu.pe.smartrent_backend.Repositories.IUserRepository;
import pe.edu.pe.smartrent_backend.Securities.WebSecurityConfig;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class UserServicesImplements implements IUser {

    @Autowired
    private IUserRepository uR;

    @Autowired
    private IRoleRepository rR;

    @Override
    public void Register(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        uR.save(user);
    }

    @Override
    public void Update(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        uR.save(user);
    }

    @Override
    public User listId(Integer id) {
        return uR.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return uR.findOneByUsername(username);
    }

    @Override
    public List<User> list() {
        return uR.findAll();
    }

    @Override
    @Transactional
    public void Delete(Integer id) {
        if (!uR.existsById(id)) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        rR.deleteByUserId(id);
        uR.deleteUserDirecto(id);
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

