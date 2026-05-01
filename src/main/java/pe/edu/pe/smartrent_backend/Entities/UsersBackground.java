package pe.edu.pe.smartrent_backend.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class UsersBackground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBackground;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "source", length = 50, nullable = false)
    private String source;

    @Column(name = "registrationDate", nullable = false)
    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    public UsersBackground() {
    }

    public UsersBackground(Integer idBackground, String type, String description, String source, LocalDate registrationDate, User user) {
        this.idBackground = idBackground;
        this.type = type;
        this.description = description;
        this.source = source;
        this.registrationDate = registrationDate;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
