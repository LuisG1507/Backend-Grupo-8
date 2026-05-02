package pe.edu.pe.smartrent_backend.DTOS.models3DDTOs;

public class Models3DStateRateDTO {
    private String state;
    private Long total;
    private Double percentage;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
