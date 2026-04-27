package pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS;

public class EstateAverageRatingDTO {
    private String estateTitle;
    private Double averageRating;

    public EstateAverageRatingDTO() {}

    public EstateAverageRatingDTO(String estateTitle, Double averageRating) {
        this.estateTitle = estateTitle;
        this.averageRating = averageRating;
    }

    public String getEstateTitle() { return estateTitle; }
    public void setEstateTitle(String estateTitle) { this.estateTitle = estateTitle; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
}