package pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS;

public class RiskPointsDecisionDTO2 {

    private String severity;
    private Integer total;
    private Double porcentaje;

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
