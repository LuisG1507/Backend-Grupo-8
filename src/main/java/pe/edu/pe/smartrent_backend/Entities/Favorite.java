package pe.edu.pe.smartrent_backend.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFavorite;

    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "idProperty")
    private Property property;

    public Favorite() {
    }

    public Favorite(Integer idFavorite, LocalDate creationDate, Users user, Property property) {
        this.idFavorite = idFavorite;
        this.creationDate = creationDate;
        this.user = user;
        this.property = property;
    }

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
