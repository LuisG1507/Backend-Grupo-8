package pe.edu.pe.smartrent_backend.DTOS.messagesDTOS;

public class MessagesStatusDistributionDTO {
    private String status;
    private Long total;
    private Double percentage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
