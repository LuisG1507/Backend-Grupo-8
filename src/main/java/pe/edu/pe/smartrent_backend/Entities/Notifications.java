package pe.edu.pe.smartrent_backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Notification")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotification;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "message", length = 800, nullable = false)
    private String message;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "read", nullable = false)
    private Boolean read;

    @Column(name = "createdDate", nullable = false)
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    public Notifications() {}

    public Notifications(Integer idNotification, String title, String message, String type, Boolean read, LocalDate createdDate, User user) {
        this.idNotification = idNotification;
        this.title = title;
        this.message = message;
        this.type = type;
        this.read = read;
        this.createdDate = createdDate;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
