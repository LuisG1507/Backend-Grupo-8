package pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;

public class FavoriteIdDTO {
    private Integer idFavorite;
    private LocalDate creationDate;
    private UserD user;
    private EstateD estate;

    public Integer getIdFavorite() {
        return idFavorite;
    }

    public void setIdFavorite(Integer idFavorite) {
        this.idFavorite = idFavorite;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public UserD getUser() {
        return user;
    }

    public void setUser(UserD user) {
        this.user = user;
    }

    public EstateD getEstate() {
        return estate;
    }

    public void setEstate(EstateD estate) {
        this.estate = estate;
    }

    public static class UserD{
        private Integer idUser;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }
    }

    public static class EstateD{
        private Integer idEstate;

        public Integer getIdEstate() {
            return idEstate;
        }

        public void setIdEstate(Integer idEstate) {
            this.idEstate = idEstate;
        }
    }
}
