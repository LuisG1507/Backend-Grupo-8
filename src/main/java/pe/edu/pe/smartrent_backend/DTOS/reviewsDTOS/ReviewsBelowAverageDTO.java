package pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS;

public class ReviewsBelowAverageDTO {
    private String title;
    private String city;
    private Double average;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}
