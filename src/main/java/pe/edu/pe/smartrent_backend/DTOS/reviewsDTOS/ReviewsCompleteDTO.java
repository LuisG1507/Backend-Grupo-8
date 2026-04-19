package pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS;

import java.time.LocalDate;

public class ReviewsCompleteDTO {

    private Integer idReview;
    private Double calification;
    private String comment;
    private LocalDate creationDate;
    private Integer idUser;
    private Integer idEstate;

    public Integer getIdReview() { return idReview; }
    public void setIdReview(Integer idReview) { this.idReview = idReview; }

    public Double getCalification() { return calification; }
    public void setCalification(Double calification) { this.calification = calification; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public Integer getIdEstate() { return idEstate; }
    public void setIdEstate(Integer idEstate) { this.idEstate = idEstate; }
}