package pe.edu.pe.smartrent_backend.DTOS.favoriteDTOS;

import java.time.LocalDate;

public class FavoriteDTOInfinite {
    private LocalDate creationDate;
    private UserBasicDTO user;
    private EstateBasicDTO estate;

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public UserBasicDTO getUser() {
        return user;
    }

    public void setUser(UserBasicDTO user) {
        this.user = user;
    }

    public EstateBasicDTO getEstate() {
        return estate;
    }

    public void setEstate(EstateBasicDTO estate) {
        this.estate = estate;
    }

    public static class UserBasicDTO {
        private Integer idUser;
        private String username;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class EstateBasicDTO {
        private Integer idEstate;
        private String title;
        private String location;

        public Integer getIdEstate() {
            return idEstate;
        }

        public void setIdEstate(Integer idEstate) {
            this.idEstate = idEstate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

}
