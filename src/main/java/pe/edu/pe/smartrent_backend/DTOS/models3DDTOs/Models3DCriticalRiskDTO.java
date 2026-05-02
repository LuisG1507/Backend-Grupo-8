package pe.edu.pe.smartrent_backend.DTOS.models3DDTOs;

public class Models3DCriticalRiskDTO {
    private String title;
    private String city;
    private int criticalPoints;

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

    public int getCriticalPoints() {
        return criticalPoints;
    }

    public void setCriticalPoints(int criticalPoints) {
        this.criticalPoints = criticalPoints;
    }
}
