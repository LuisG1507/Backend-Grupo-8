package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

import java.time.LocalDate;

public class NotificationSecurityAlertDTO {

    private LocalDate createdDate;
    private Long totalAlerts;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(Long totalAlerts) {
        this.totalAlerts = totalAlerts;
    }
}
