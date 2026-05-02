package pe.edu.pe.smartrent_backend.DTOS.reviewsDTOS;

public class ReviewsRatingDistributionDTO {
    private Long bad;
    private Long regular;
    private Long good;
    private Double globalAverage;

    public Long getBad() {
        return bad;
    }

    public void setBad(Long bad) {
        this.bad = bad;
    }

    public Long getRegular() {
        return regular;
    }

    public void setRegular(Long regular) {
        this.regular = regular;
    }

    public Long getGood() {
        return good;
    }

    public void setGood(Long good) {
        this.good = good;
    }

    public Double getGlobalAverage() {
        return globalAverage;
    }

    public void setGlobalAverage(Double globalAverage) {
        this.globalAverage = globalAverage;
    }
}
