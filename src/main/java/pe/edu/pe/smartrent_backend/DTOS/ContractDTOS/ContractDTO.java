package pe.edu.pe.smartrent_backend.DTOS.ContractDTOS;

import java.time.LocalDateTime;

public class ContractDTO {

    private int idContract;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double monthlyAmount;
    private boolean status;
    private LocalDateTime createdAt;
    private int idEstate;
    private int idLessor;
    private int idLessee;

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getIdEstate() {
        return idEstate;
    }

    public void setIdEstate(int idEstate) {
        this.idEstate = idEstate;
    }

    public int getIdLessor() {
        return idLessor;
    }

    public void setIdLessor(int idLessor) {
        this.idLessor = idLessor;
    }

    public int getIdLessee() {
        return idLessee;
    }

    public void setIdLessee(int idLessee) {
        this.idLessee = idLessee;
    }
}