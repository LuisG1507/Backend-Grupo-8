package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

import pe.edu.pe.smartrent_backend.Entities.Conversation;
import pe.edu.pe.smartrent_backend.Entities.Users;

import java.time.LocalDate;

public class NotificationsCompleteDTO {
    private Integer idNotification;
    private String title;
    private String message;
    private String type;
    private Boolean read;
    private LocalDate createdDate;
    private Users user;

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
