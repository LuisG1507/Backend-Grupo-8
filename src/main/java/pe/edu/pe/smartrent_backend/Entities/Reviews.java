package pe.edu.pe.smartrent_backend.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReview;

    @Column(name = "calification", nullable = false)
    private Double calification;

    @Column(name = "comment", length = 200, nullable = false)
    private String comment;

    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "idProperty")
    private Property property;

    public Reviews() {
    }

    public Reviews(Integer idReview, Double calification, String comment, LocalDate creationDate, Users user, Property property) {
        this.idReview = idReview;
        this.calification = calification;
        this.comment = comment;
        this.creationDate = creationDate;
        this.user = user;
        this.property = property;
    }

    public Integer getIdReview() {
        return idReview;
    }

    public void setIdReview(Integer idReview) {
        this.idReview = idReview;
    }

    public Double getCalification() {
        return calification;
    }

    public void setCalification(Double calification) {
        this.calification = calification;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
