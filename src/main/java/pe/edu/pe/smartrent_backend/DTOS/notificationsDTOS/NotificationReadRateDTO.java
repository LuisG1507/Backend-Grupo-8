package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

public class NotificationReadRateDTO {
    private String type;
    private Long total;
    private Long read;
    private Double readRate;

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

    public Long getRead() {
        return read;
    }

    public void setRead(Long read) {
        this.read = read;
    }

    public Double getReadRate() {
        return readRate;
    }

    public void setReadRate(Double readRate) {
        this.readRate = readRate;
    }
}
