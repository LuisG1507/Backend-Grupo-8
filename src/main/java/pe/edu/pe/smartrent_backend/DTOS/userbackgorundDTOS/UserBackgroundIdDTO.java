package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;

public class UserBackgroundIdDTO {
    private Integer idBackground;
    private String type;
    private String description;
    private String source;
    private LocalDate registrationDate;
    private Userp user;

    public Integer getIdBackground() {
        return idBackground;
    }

    public void setIdBackground(Integer idBackground) {
        this.idBackground = idBackground;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Userp getUser() {
        return user;
    }

    public void setUser(Userp user) {
        this.user = user;
    }

    public static class Userp {
        private Integer idUser;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }
    }
}
