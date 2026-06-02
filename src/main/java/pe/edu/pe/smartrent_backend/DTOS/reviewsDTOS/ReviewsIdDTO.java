package pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;

public class ReviewsIdDTO {
    private Integer idReview;
    private Double calification;
    private String comment;
    private LocalDate creationDate;
    private Userp user;
    private Estatep estate;

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

    public Userp getUser() {
        return user;
    }

    public void setUser(Userp user) {
        this.user = user;
    }

    public Estatep getEstate() {
        return estate;
    }

    public void setEstate(Estatep estate) {
        this.estate = estate;
    }

    public static class Userp{
        private Integer idUser;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }
    }

    public static class Estatep{
        private Integer idEstate;

        public Integer getIdEstate() {
            return idEstate;
        }

        public void setIdEstate(Integer idEstate) {
            this.idEstate = idEstate;
        }
    }
}
