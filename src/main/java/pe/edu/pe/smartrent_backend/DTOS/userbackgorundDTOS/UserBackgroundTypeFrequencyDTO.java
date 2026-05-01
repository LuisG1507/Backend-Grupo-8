package pe.edu.pe.smartrent_backend.DTOS.userbackgorundDTOS;

public class UserBackgroundTypeFrequencyDTO {
    private String type;
    private Long total;
    private Double percentage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
