package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;

public class UserBackgroundDTO {

    private String type;
    private String description;
    private String source;
    private LocalDate registrationDate;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
