package pe.edu.pe.smartrent_backend.DTOS.contractDTOS;

import java.time.LocalDateTime;

public class ContractExpiringDTO {

    private String name;
    private String lastName;
    private String estateTitle;
    private LocalDateTime endDate;
    private Long daysRemaining;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEstateTitle() {
        return estateTitle;
    }

    public void setEstateTitle(String estateTitle) {
        this.estateTitle = estateTitle;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }
}
