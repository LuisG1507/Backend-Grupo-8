package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

public class NotificationTypeMonthlyDTO {
    private String type;
    private Long total;

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
}
