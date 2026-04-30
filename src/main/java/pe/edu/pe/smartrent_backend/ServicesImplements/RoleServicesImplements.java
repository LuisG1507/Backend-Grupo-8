package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Role;
import pe.edu.pe.smartrent_backend.Repositories.IRoleRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRole;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IUser;

import java.util.List;

@Service
public class RoleServicesImplements implements IRole {

    @Autowired
    private IRoleRepository rR;

    @Override
    public void Register(Role role) {
        rR.save(role);
    }

    @Override
    public void Update(Role role) {
        rR.save(role);
    }

    @Override
    public Role listId(Integer id) {
        return rR.findById(id).orElse(null);
    }

    @Override
    public List<Role> list() {
        return rR.findAll();
    }

    @Override
    public void Delete(Integer id) {
        rR.deleteById(id);
    }
}
