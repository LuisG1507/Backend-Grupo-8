package pe.edu.pe.smartrent_backend.DTOS.roleDTOS;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.User;

public class RoleDTOudl {


    private String rol;

    private User user;


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
