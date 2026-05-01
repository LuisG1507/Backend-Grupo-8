package pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS;

public class RiskReportDecisionDTO1 {

    private String title;
    private String city;
    private int total_reportes;

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

    public int getTotal_reportes() {
        return total_reportes;
    }

    public void setTotal_reportes(int total_reportes) {
        this.total_reportes = total_reportes;
    }
}
