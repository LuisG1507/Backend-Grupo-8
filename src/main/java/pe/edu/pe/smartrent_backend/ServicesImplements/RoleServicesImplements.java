package pe.edu.pe.smartrent_backend.ServicesImplements;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pe.smartrent_backend.Entities.Role;
import pe.edu.pe.smartrent_backend.Repositories.IRoleRepository;
import pe.edu.pe.smartrent_backend.ServicesInterfaces.IRole;


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
    @Transactional
    public void Delete(Integer id) {
        rR.deleteRoleDirecto(id);
    }


    // Decisiones

    // Distribución de roles en la plataforma con porcentaje
    @Override
    public List<Object[]> RDecision1() {
        return rR.RDecision1();
    }

    //Usuarios con más de un rol asignado
    @Override
    public List<Object[]> RDecision2() {
        return rR.RDecision2();
    }
}
