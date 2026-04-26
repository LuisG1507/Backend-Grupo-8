package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Users;
import pe.edu.pe.smartrent_backend.Repositories.IUserRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.List;


@Service
public class UserServicesImplements implements IUser {

    @Autowired
    private IUserRepository uR;


    @Override
    public void Register(Users user) {
        uR.save(user);
    }

    @Override
    public void Update(Users user) {
        uR.save(user);
    }

    @Override
    public Users listId(Integer id) {
        return uR.findById(id).orElse(null);
    }

    @Override
    public List<Users> list() {
        return uR.findAll();
    }

    @Override
    public void Delete(Integer id) {
        uR.deleteById(id);
    }

    @Override
    public Users BuscarPorDNI(Integer id) {
        return uR.findByDNI(id);
    }

    @Override
    public List<Users> fyndByStatus() {
        return uR.findByStatusVerification();
    }
}

