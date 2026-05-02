package pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS;

public class RiskReportDecisionDTO2 {

    private String risk_level;
    private int total;
    private Double porcentaje;

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
