package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

public class NotificationsTypeDTO {

    private String type;
    private Long quantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
