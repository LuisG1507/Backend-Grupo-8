package pe.edu.pe.smartrent_backend.DTOS.notificationsDTOS;

import java.time.LocalDate;

public class NotificationSecurityAlertDTO {

    private LocalDate createdDate;
    private int totalAlerts;

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getTotalAlerts() {
        return totalAlerts;
    }

    public void setTotalAlerts(int totalAlerts) {
        this.totalAlerts = totalAlerts;
    }
}
