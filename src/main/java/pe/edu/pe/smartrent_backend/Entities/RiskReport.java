package pe.edu.pe.smartrent_backend.Entities;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "RiskReport")
public class RiskReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRiskReport;

    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @Column(name = "riskLevel", nullable = false)
    private String riskLevel;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "details", length = 200, nullable = false)
    private String details;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "idProperty")
    private Property property;

    public RiskReport() {
    }

    public RiskReport(Integer idRiskReport, String type, LocalDate creationDate, String riskLevel, String description, String details, Users user, Property property) {
        this.idRiskReport = idRiskReport;
        this.type = type;
        this.creationDate = creationDate;
        this.riskLevel = riskLevel;
        this.description = description;
        this.details = details;
        this.user = user;
        this.property = property;
    }

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
