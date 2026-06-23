package pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import pe.edu.pe.smartrent_backend.Entities.Estate;
import pe.edu.pe.smartrent_backend.Entities.User;

import java.time.LocalDate;

public class RiskReportIdDTO {
    private Integer idRiskReport;
    private String type;
    private LocalDate creationDate;
    private String riskLevel;
    private String description;
    private String details;
    private UserP idUser;
    private EstateP estate;

    public Integer getIdRiskReport() {
        return idRiskReport;
    }

    public void setIdRiskReport(Integer idRiskReport) {
        this.idRiskReport = idRiskReport;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UserP getIdUser() {
        return idUser;
    }

    public void setIdUser(UserP idUser) {
        this.idUser = idUser;
    }

    public EstateP getEstate() {
        return estate;
    }

    public void setEstate(EstateP estate) {
        this.estate = estate;
    }

    public static class EstateP{
        private Integer idEstate;

        public Integer getIdEstate() {
            return idEstate;
        }

        public void setIdEstate(Integer idEstate) {
            this.idEstate = idEstate;
        }
    }

    public static class UserP{
        private Integer idUser;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }
    }
}
