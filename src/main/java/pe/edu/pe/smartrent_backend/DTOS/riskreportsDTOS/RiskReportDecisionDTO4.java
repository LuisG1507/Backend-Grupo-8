package pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS;

public class RiskReportDecisionDTO4 {

    private String title;
    private String city;
    private String district;
    private int reportes_altos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getReportes_altos() {
        return reportes_altos;
    }

    public void setReportes_altos(int reportes_altos) {
        this.reportes_altos = reportes_altos;
    }
}
