package pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS;

public class RiskPointsDTO {

    private String description;
    private Double cordX;
    private Double cordY;
    private Double cordZ;
    private String severity;
    private Integer idModel3D;

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getCordX() { return cordX; }
    public void setCordX(Double cordX) { this.cordX = cordX; }

    public Double getCordY() { return cordY; }
    public void setCordY(Double cordY) { this.cordY = cordY; }

    public Double getCordZ() { return cordZ; }
    public void setCordZ(Double cordZ) { this.cordZ = cordZ; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public Integer getIdModel3D() { return idModel3D; }
    public void setIdModel3D(Integer idModel3D) { this.idModel3D = idModel3D; }
}