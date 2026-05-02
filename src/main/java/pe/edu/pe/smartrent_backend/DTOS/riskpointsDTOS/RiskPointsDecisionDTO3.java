package pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS;

public class RiskPointsDecisionDTO3 {

    private String title;
    private String city;
    private String district;
    private Integer critic_points;

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

    public Integer getCritic_points() {
        return critic_points;
    }

    public void setCritic_points(Integer critic_points) {
        this.critic_points = critic_points;
    }
}
