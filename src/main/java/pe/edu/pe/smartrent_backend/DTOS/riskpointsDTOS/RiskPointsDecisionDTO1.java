package pe.edu.pe.smartrent_backend.DTOS.riskpointsDTOS;

public class RiskPointsDecisionDTO1 {

    private Integer id_modelo3d;
    private String title;
    private String city;
    private Integer total_puntos;

    public Integer getId_modelo3d() {
        return id_modelo3d;
    }

    public void setId_modelo3d(Integer id_modelo3d) {
        this.id_modelo3d = id_modelo3d;
    }

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

    public Integer getTotal_puntos() {
        return total_puntos;
    }

    public void setTotal_puntos(Integer total_puntos) {
        this.total_puntos = total_puntos;
    }
}
