package pe.edu.pe.smartrent_backend.DTOS.riskreportsDTOS;

public class RiskReportDecisionDTO3 {

    private String name;
    private String last_name;
    private Integer total_reports;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getTotal_reports() {
        return total_reports;
    }

    public void setTotal_reports(Integer total_reports) {
        this.total_reports = total_reports;
    }
}
