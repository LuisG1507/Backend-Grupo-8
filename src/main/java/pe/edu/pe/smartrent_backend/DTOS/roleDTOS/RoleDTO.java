package pe.edu.pe.smartrent_backend.DTOS.roleDTOS;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.User;

public class RoleDTO {

    private Integer id;

    private String rol;

    private Integer idUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
